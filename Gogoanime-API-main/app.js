const scapper = require('./scrapper');
const express = require('express');
const { env } = require('process');
const cors = require('cors');

const app = express();
app.use(cors());

app.get('/', (req, res) => {
	res.send(
		'👋 Hello world🌍, Welcome to 🦄 GogoAnime API 🧬 </br> Available routes : /Popular , /NewSeasons , /search/:query , /getAnime/:animeId , /getEpisode/:episodeId, ADDED BY DANIELA MAGRi AND ALESSANDRO SCICOLONE : /genre , /allAnime', '/getEpisodes/:listOfEpisodeId'
	);
});

app.get('/Popular/:page', async (req, res) => {
	const result = await scapper.popular(req.params.page);
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

/** MODIFIED */
app.get('/genre', async (req, res) => {
	const result = await scapper.genre();
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

/** MODIFIED */
app.get('/allAnime/:page', async (req, res) => {
	const result = await scapper.allAnime(req.params.page);
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

app.get('/genre/:genre/:page', async (req, res) => {
	const result = await scapper.byGenre(req.params.genre, req.params.page);
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

app.get('/NewSeasons/:page', async (req, res) => {
	const result = await scapper.newSeason(req.params.page);
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

app.get('/search/:query', async (req, res) => {
	const result = await scapper.search(req.params.query);
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

app.get('/getAnime/:query', async (req, res) => {
	const result = await scapper.anime(req.params.query);
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

app.get('/getEpisode/:query', async (req, res) => {
	const result = await scapper.watchAnime(req.params.query);
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

/** MODIFIED */
app.get('/getEpisodes/:query', async (req, res) => {
	console.log(req.params.query);
	const result = await scapper.listOfEpisodes(req.params.query);
	res.header('Content-Type', 'application/json');
	res.send(JSON.stringify(result, null, 4));
});

port = env.PORT || 3000;
app.listen(port, () => {
	console.log(`Listening to port ${port}`);
});
