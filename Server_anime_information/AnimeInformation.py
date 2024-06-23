from flask import Flask, jsonify, abort
import animeworld as aw  
import json

app = Flask(__name__)

@app.route('/anime/<string:name>', methods=['GET'])
def get_anime(name):
    print(f"Request received for anime: {name}")
    try:
        data = aw.find(name)
        if data:
            print(data)
            return jsonify(data)
        else:
            abort(404, description = "Anime not found")
    except Exception as e:
        print(f"An error occurred: {e}")
        abort(500, description="Internal Server Error")

def _get_anime(name):
    data = aw.find(name)
    if data:
        return jsonify(data)
    else:
        return "Anime not found"

@app.route('/anime_not_in_animeworld/<string:letter>', methods=['GET'])
def get_anime_list_of_anime_not_in_animeworld(letter):
    file_path = "lista_anime_in_Gogoanime_.json"  
    anime_not_in_animeworld = []
    with open(file_path, "r", encoding="utf-8") as file:
        anime_list = json.load(file)
        for anime in anime_list:
            name = anime["name"]
            if(name.startswith(letter)):
                response = _get_anime(name)
                if response == "Anime not found":
                    print(f"Detected: {name}")
                    anime_not_in_animeworld.append(name)

    output_file_path = "anime_not_in_animeworld_" + letter +".json"
    with open(output_file_path, "w") as output_file:
        json.dump(anime_not_in_animeworld, output_file)

    return jsonify(anime_not_in_animeworld)


@app.route('/search_anime/<string:query>', methods=['GET'])
def search_anime(query):
    file_path = "lista_anime_in_Gogoanime_.json"  
    result = []
    with open(file_path, "r", encoding="utf-8") as file:
        anime_list = json.load(file)
        for anime in anime_list:
            name = anime["name"]
            if(name.lower().startswith(query.lower())):
                anime_id = anime["anime_id"]
                result.append({"name": name, "anime_id": anime_id})
    return jsonify(result)
    


if __name__ == '__main__':
    # Indirizzo IP e porta su cui eseguire il server Flask
    ip_address = '192.168.1.7'  # Inserisci qui l'indirizzo IP desiderato
    port = 5000

    # Esegui il server Flask specificando l'indirizzo IP e la porta
    app.run(debug=True, host=ip_address, port=port)