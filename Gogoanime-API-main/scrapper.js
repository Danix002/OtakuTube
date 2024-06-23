const cheerio = require('cheerio');
const axios = require('axios');
const puppeteer = require('puppeteer');
const userAgent = require('user-agents');

// https://www3.gogoanimes.fi/
// https://gogoanime.run
const baseUrl = 'https://gogoanime.run';

async function newSeason(page) {
	var anime_list = [];

	res = await axios.get(`${baseUrl}/new-season.html?page=${page}`);
	const body = await res.data;
	const $ = cheerio.load(body);

	$('div.main_body div.last_episodes ul.items li').each((index, element) => {
		$elements = $(element);
		name = $elements.find('p').find('a');
		img = $elements.find('div').find('a').find('img').attr('src');
		link = $elements.find('div').find('a').attr('href');
		anime_name = { name: name.html(), img_url: img, anime_id: link.slice(10) };
		anime_list.push(anime_name);
	});

	return await anime_list;
}

async function byGenre(genre, page) {
	var anime_list = [];

	res = await axios.get(`${baseUrl}/genre/${genre}?page=${page}`);
	const body = await res.data;
	const $ = cheerio.load(body);

	console.log(body);
	$('div.main_body div.last_episodes ul.items li').each((index, element) => {
		$elements = $(element);
		name = $elements.find('p').find('a');
		img = $elements.find('div').find('a').find('img').attr('src');
		link = $elements.find('div').find('a').attr('href');
		anime_name = { name: name.html(), img_url: img, anime_id: link.slice(10) };
		anime_list.push(anime_name);
	});

	return await anime_list;
}

/** MODIFIED */
function genre() {
	var genre_list = [
		{ titolo: 'Action', id: 'action' },
		{ titolo: 'Adult Cast', id: 'adult-cast' },
		{ titolo: 'Adventure', id: 'adventure' },
		{ titolo: 'Anthropomorphic', id: 'anthropomorphic' },
		{ titolo: 'Avant Garde', id: 'avant-garde' },
		{ titolo: 'Boys Love', id: 'shounen-ai' },
		{ titolo: 'Cars', id: 'cars' },
		{ titolo: 'CGDCT', id: 'cgdct' },
		{ titolo: 'Childcare', id: 'childcare' },
		{ titolo: 'Comedy', id: 'comedy' },
		{ titolo: 'Comic', id: 'comic' },
		{ titolo: 'Crime', id: 'crime' },
		{ titolo: 'Crossdressing', id: 'crossdressing' },
		{ titolo: 'Delinquents', id: 'delinquents' },
		{ titolo: 'Dementia', id: 'dementia' },
		{ titolo: 'Demons', id: 'demons' },
		{ titolo: 'Detective', id: 'detective' },
		{ titolo: 'Drama', id: 'drama' },
		{ titolo: 'Dub', id: 'dub' },
		{ titolo: 'Family', id: 'family' },
		{ titolo: 'Fantasy', id: 'fantasy' },
		{ titolo: 'Gag Humor', id: 'gag-humor' },
		{ titolo: 'Game', id: 'game' },
		{ titolo: 'Gender Bender', id: 'gender-bender' },
		{ titolo: 'Gore', id: 'gore' },
		{ titolo: 'Gourmet', id: 'gourmet' },
		{ titolo: 'Harem', id: 'harem' },
		{ titolo: 'High Stakes Game', id: 'high-stakes-game' },
		{ titolo: 'Historical', id: 'historical' },
		{ titolo: 'Horror', id: 'horror' },
		{ titolo: 'Isekai', id: 'isekai' },
		{ titolo: 'Iyashikei', id: 'iyashikei' },
		{ titolo: 'Josei', id: 'josei' },
		{ titolo: 'Kids', id: 'kids' },
		{ titolo: 'Love Polygon', id: 'love-polygon' },
		{ titolo: 'Magic', id: 'magic' },
		{ titolo: 'Mahou Shoujo', id: 'mahou-shoujo' },
		{ titolo: 'Martial Arts', id: 'martial-arts' },
		{ titolo: 'Mecha', id: 'mecha' },
		{ titolo: 'Medical', id: 'medical' },
		{ titolo: 'Military', id: 'military' },
		{ titolo: 'Music', id: 'music' },
		{ titolo: 'Mystery', id: 'mystery' },
		{ titolo: 'Mythology', id: 'mythology' },
		{ titolo: 'Organized Crime', id: 'organized-crime' },
		{ titolo: 'Parody', id: 'parody' },
		{ titolo: 'Performing Arts', id: 'performing-arts' },
		{ titolo: 'Pets', id: 'pets' },
		{ titolo: 'Police', id: 'police' },
		{ titolo: 'Psychological', id: 'psychological' },
		{ titolo: 'Racing', id: 'racing' },
		{ titolo: 'Reincarnation', id: 'reincarnation' },
		{ titolo: 'Romance', id: 'romance' },
		{ titolo: 'Romantic Subtext', id: 'romantic-subtext' },
		{ titolo: 'Samurai', id: 'samurai' },
		{ titolo: 'School', id: 'school' },
		{ titolo: 'Sci-Fi', id: 'sci-fi' },
		{ titolo: 'Seinen', id: 'seinen' },
		{ titolo: 'Shoujo', id: 'shoujo' },
		{ titolo: 'Shoujo Ai', id: 'shoujo-ai' },
		{ titolo: 'Shounen', id: 'shounen' },
		{ titolo: 'Showbiz', id: 'showbiz' },
		{ titolo: 'Slice of Life', id: 'slice-of-life' },
		{ titolo: 'Space', id: 'space' },
		{ titolo: 'Sports', id: 'sports' },
		{ titolo: 'Strategy Game', id: 'strategy-game' },
		{ titolo: 'Super Power', id: 'super-power' },
		{ titolo: 'Supernatural', id: 'supernatural' },
		{ titolo: 'Survival', id: 'survival' },
		{ titolo: 'Suspense', id: 'suspense' },
		{ titolo: 'Team Sports', id: 'team-sports' },
		{ titolo: 'Thriller', id: 'thriller' },
		{ titolo: 'Time Travel', id: 'time-travel' },
		{ titolo: 'Vampire', id: 'vampire' },
		{ titolo: 'Video Game', id: 'video-game' },
		{ titolo: 'Visual Arts', id: 'visual-arts' },
		{ titolo: 'Work Life', id: 'work-life' },
		{ titolo: 'Workplace', id: 'workplace' },
	];
	return genre_list;
}

/** MODIFIED */
async function popular(page) {
	var anime_list = [];

	try {
        const res = await axios.get(`${baseUrl}/popular.html?page=${page}`);
        const body = res.data;
        const $ = cheerio.load(body);

        const items = $('div.main_body div.last_episodes ul.items li').toArray();

        for (let element of items) {
            const $elements = $(element);
            const name = $elements.find('p a').text();
            const img = $elements.find('div a img').attr('src');
            const link = $elements.find('div a').attr('href');

            try {
                const anime_about_res = await axios.get(`${baseUrl}/${link}`);
                const body_anime = anime_about_res.data;
                const $_ = cheerio.load(body_anime);
                const anime_about = $_('div.anime_info_body_bg div.description').text();

                const anime_name = {
                    name: name,
                    img_url: img,
                    anime_id: link.slice(10),
                    description: anime_about
                };
                anime_list.push(anime_name);
            } catch (error) {
                console.error(`Errore nel caricamento delle informazioni aggiuntive per ${link}:`, error);
            }
        }
    } catch (error) {
        console.error(`Errore nel caricamento della pagina ${page}:`, error);
    }

    return anime_list;
}

/** MODIFIED */
async function allAnime(page) {
	var anime_list = [];
	//let page = 1;
	//while (page <= 98) {
	res = await axios.get(`${baseUrl}/anime-list.html?page=${page}`);
	const body = await res.data;
	const $ = cheerio.load(body);
	$('div.main_body div.anime_list_body ul.listing li').each((index, element) => {
		$element = $(element);
		name = $element.find('a').text().trim();
		link = $element.find('a').attr('href');
		anime_name = { name, anime_id: link.slice(10) };
		anime_list.push(anime_name);
	});
	//page++;
	//}
	return anime_list;
}

async function search(query) {
	console.log("Arrivata richiesta di search per: " + query);
	var anime_list = [];

	res = await axios.get(`${baseUrl}/search.html?keyword=${query}`);
	const body = await res.data;
	const $ = cheerio.load(body);

	$('div.main_body div.last_episodes ul.items li').each((index, element) => {
		$elements = $(element);
		name = $elements.find('p').find('a');
		img = $elements.find('div').find('a').find('img').attr('src');
		link = $elements.find('div').find('a').attr('href');
		anime_name = { name: name.html(), img_url: img, anime_id: link.slice(10) };
		anime_list.push(anime_name);
	});

	return await anime_list;
}

/** MODIFIED */
async function anime(_anime_name) {
	episode_array = [];

	res = await axios.get(`${baseUrl}/category/${_anime_name}`);
	const body = await res.data;
	const $ = cheerio.load(body);

	img_url = $('div.anime_info_body_bg  img').attr('src');
	anime_name = $('div.anime_info_body_bg  h1').text();
	anime_about = $('div.anime_info_body_bg  div.description').text();

	anime_details = $('div.anime_info_body_bg  p.type');

	let anime_type, anime_genres, anime_release, anime_status, anime_othernames;

	anime_details.each((index, element) => {
		const text = $(element).text();
		if (text.includes('Type:')) {
			anime_type = text.replace('Type: ', '').trim();
		} else if (text.includes('Genre:')) {
			anime_genres = text.replace('Genre: ', '').trim().split(', ');
		} else if (text.includes('Released:')) {
			anime_release = text.replace('Released: ', '').trim();
		} else if (text.includes('Status:')) {
			anime_status = text.replace('Status: ', '').trim();
		} else if (text.includes('Other name:')) {
			anime_othernames = text.replace('Other name: ', '').trim().split(', ');
		}
	});

	el = $('#episode_page');
	ep_start = 1;
	ep_end = el.children().last().find('a').text().split('-')[1];

	for (let i = ep_start; i <= ep_end; i++) {
		episode_array.push(`${_anime_name}-episode-${i}`);
	}

	anime_result = {
		name: anime_name,
		img_url: img_url,
		about: anime_about,
		episode_id: episode_array,
		type: anime_type,
		release: anime_release,
		genres: anime_genres,
		status: anime_status,
		othername: anime_othernames,
	};

	return await anime_result;
}

/** MODIFIED */
async function watchAnime(episode_id) {
	try {
        res = await axios.get(`${baseUrl}/${episode_id}`);
        const body = res.data;
        $ = cheerio.load(body);

        episode_link = $('li.dowloads > a').attr('href');

        if (episode_link !== undefined) {
            ep = await getDownloadLink(episode_link);
            index = 0; 
            watchAnime_result = { index, ep };
            return watchAnime_result;
        } else {
            console.error('episode_link is undefined');
            return {index: -1, name: ["", "", ""], link: ["", "", ""] }; 
        }
    } catch (error) {
        console.error('Error in watchAnime:', error);
        return {index: -1, name: ["", "", ""], link: ["", "", ""] }; 
    }
}

/** MODIFIED */
async function listOfEpisodes(list_episode_id) {
	list_episode_id_array = list_episode_id.split('@');

	const promises = list_episode_id_array.map(async (value, index) => {
        const res = await axios.get(`${baseUrl}/${value}`);
        const body = await res.data;
        const $ = cheerio.load(body);

        const episode_link = $('li.dowloads > a').attr('href');
		ep = await getDownloadLink(episode_link);

        return { index, ep };
    });


    const episodeLinks = await Promise.all(promises);

    return episodeLinks;
}

/** MODIFIED */
async function getDownloadLink(episode_link) {
	const browser = await puppeteer.launch({ headless: true });
	const page = await browser.newPage();
	await page.setUserAgent(userAgent.random().toString());
	await page.goto(episode_link, { waitUntil: 'networkidle0' });
	
	try {
		await page.waitForSelector('.mirror_link', { timeout: 30000 });
	} catch (error) {
		console.error('Element not found:', error);
		await browser.close();
		return [{ name: ["", "", ""], link: ["", "", ""] }];
	}

	const links = await page.evaluate(() => {
		let ep_links = [];

		const ep = document.querySelector('.mirror_link');
		
		ep.querySelectorAll('a').forEach((link) => {
			ep_links.push({ name: link.innerText.split('D ')[1].replace(/[()]/g, ''), link: link.href });
		});

		return ep_links;
	});

	await browser.close();
	return links; 
}

module.exports = {
	popular,
	byGenre,

	/** MODIFIED */
	genre,
	allAnime,
	listOfEpisodes,

	newSeason,
	search,
	anime,
	watchAnime,
};
