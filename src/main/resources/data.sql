DELETE FROM collection_items;
DELETE FROM movieposters;
DELETE FROM movies;
DELETE FROM genres;

INSERT INTO genres (id, name, description) VALUES
    (1, 'Action', 'Action, action and more action!'),
    (2, 'Sci-Fi', 'Speaks to the imagination'),
    (3, 'Drama', 'Often with a smile and a tear.'),
    (4, 'Horror', 'Make sure your flashlight has enough battery'),
    (5, 'War', 'War, war never changes'),
    (6, 'Adventure', 'A hobbit, a wizard and a archer..');

INSERT INTO movies (id, title, director, release_year, description, created_at, genre_id) VALUES
    (1, 'The Dark Knight', 'Christopher Nolan', 2008, 'When a menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman, James Gordon and Harvey Dent must work together to put an end to the madness.', CURRENT_TIMESTAMP, 1),
    (2, 'Interstellar', 'Christopher Nolan', 2014, 'When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.', CURRENT_TIMESTAMP, 2),
    (3, 'Dune', 'Denis Villeneuve', 2021, 'Paul Atreides arrives on Arrakis after his father accepts the stewardship of the dangerous planet. However, chaos ensues after a betrayal as forces clash to control melange, a precious resource.', CURRENT_TIMESTAMP, 2),

    (4, 'Inception', 'Christopher Nolan', 2010, 'A skilled thief enters the dreams of others to steal secrets, but is offered a chance at redemption through one impossible job.', CURRENT_TIMESTAMP, 2),
    (5, 'Mad Max: Fury Road', 'George Miller', 2015, 'In a post-apocalyptic wasteland, Max teams up with Furiosa to flee a tyrant and his army.', CURRENT_TIMESTAMP, 1),
    (6, 'The Martian', 'Ridley Scott', 2015, 'An astronaut becomes stranded on Mars and must rely on science and ingenuity to survive.', CURRENT_TIMESTAMP, 2),
    (7, '1917', 'Sam Mendes', 2019, 'Two British soldiers are sent on a dangerous mission across enemy territory during World War I.', CURRENT_TIMESTAMP, 5),
    (8, 'Saving Private Ryan', 'Steven Spielberg', 1998, 'A group of U.S. soldiers go behind enemy lines to retrieve a paratrooper after the Normandy landings.', CURRENT_TIMESTAMP, 5),
    (9, 'The Shawshank Redemption', 'Frank Darabont', 1994, 'Two imprisoned men bond over years, finding solace and eventual redemption through acts of decency.', CURRENT_TIMESTAMP, 3),
    (10, 'The Lord of the Rings: The Fellowship of the Ring', 'Peter Jackson', 2001, 'A young hobbit begins a perilous journey to destroy a powerful ring before evil can reclaim it.', CURRENT_TIMESTAMP, 6),
    (11, 'The Lord of the Rings: The Two Towers', 'Peter Jackson', 2002, 'The fellowship is broken, but the quest continues as war rises across Middle-earth.', CURRENT_TIMESTAMP, 6),
    (12, 'The Lord of the Rings: The Return of the King', 'Peter Jackson', 2003, 'The final confrontation for Middle-earth begins as Frodo nears Mount Doom.', CURRENT_TIMESTAMP, 6),
    (13, 'Gladiator', 'Ridley Scott', 2000, 'A betrayed Roman general seeks revenge against the corrupt emperor who murdered his family.', CURRENT_TIMESTAMP, 1),
    (14, 'A Quiet Place', 'John Krasinski', 2018, 'A family must live in silence to avoid creatures that hunt by sound.', CURRENT_TIMESTAMP, 4),
    (15, 'The Conjuring', 'James Wan', 2013, 'Paranormal investigators help a family terrorized by a dark presence in their farmhouse.', CURRENT_TIMESTAMP, 4),
    (16, 'Blade Runner 2049', 'Denis Villeneuve', 2017, 'A young blade runner discovers a secret that could destabilize society and sends him in search of Rick Deckard.', CURRENT_TIMESTAMP, 2),
    (17, 'The Pianist', 'Roman Polanski', 2002, 'A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto during World War II.', CURRENT_TIMESTAMP, 3),
    (18, 'Jurassic Park', 'Steven Spielberg', 1993, 'A theme park populated by cloned dinosaurs spirals into chaos when security systems fail.', CURRENT_TIMESTAMP, 6);

INSERT INTO collection_items (id, username, status, favorite, rating, movie_id) VALUES
    -- testuser
    (1, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'TO_WATCH', false, NULL, 1),
    (2, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'WATCHED', true, 5, 2),
    (3, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'WATCHED', true, 4, 3),
    (6, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'WATCHED', true, 5, 4),
    (7, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'TO_WATCH', false, NULL, 5),
    (8, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'WATCHED', false, 4, 7),
    (9, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'WATCHED', true, 5, 9),
    (10, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'TO_WATCH', false, NULL, 10),
    (11, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'WATCHED', false, 3, 14),
    (12, '47d794ff-7585-4466-b738-f9b1b5491e6a', 'TO_WATCH', false, NULL, 16),

    -- testadmin
    (4, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'WATCHED', true, 5, 3),
    (5, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'TO_WATCH', false, 2, 2),
    (13, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'WATCHED', true, 5, 1),
    (14, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'WATCHED', false, 4, 6),
    (15, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'TO_WATCH', false, NULL, 8),
    (16, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'WATCHED', true, 5, 11),
    (17, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'WATCHED', false, 4, 12),
    (18, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'TO_WATCH', false, NULL, 13),
    (19, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'WATCHED', false, 3, 15),
    (20, 'd6cd7066-68a9-4b82-9ae0-28ce301512ee', 'TO_WATCH', false, NULL, 18);

SELECT setval('movies_id_seq', (SELECT MAX(id) FROM movies));
SELECT setval('collection_items_id_seq', (SELECT MAX(id) FROM collection_items));
SELECT setval('genres_id_seq', (SELECT MAX(id) FROM genres));