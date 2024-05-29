from flask import Flask, jsonify, abort
import animeworld as aw  

app = Flask(__name__)

@app.route('/anime/<string:name>', methods=['GET'])
def get_anime(name):
    print(f"Request received for anime: {name}")
    try:
        data = aw.find(name)
        if data:
            return jsonify(data)
        else:
            abort(404, description="Anime not found")
    except Exception as e:
        print(f"An error occurred: {e}")
        abort(500, description="Internal Server Error")

if __name__ == '__main__':
    # Indirizzo IP e porta su cui eseguire il server Flask
    ip_address = '192.168.1.3'  # Inserisci qui l'indirizzo IP desiderato
    port = 5000

    # Esegui il server Flask specificando l'indirizzo IP e la porta
    app.run(debug=True, host=ip_address, port=port)
