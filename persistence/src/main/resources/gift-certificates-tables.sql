CREATE TABLE GiftCertificates (
  id SERIAL PRIMARY KEY,
  name varchar(40) NOT NULL,
  description varchar(500) NOT NULL,
  price DECIMAL(7,2) NOT NULL,
  duration INT NOT NULL,
  create_date TIMESTAMP NOT NULL,
  last_update_date TIMESTAMP NOT NULL
);

CREATE TABLE Tags (
  id SERIAL PRIMARY KEY,
  name varchar(40) NOT NULL
);

CREATE TABLE GiftCertificatesTags (
  gift_certificate_id INT NOT NULL,
  tag_id INT NOT NULL,

  PRIMARY KEY (gift_certificate_id, tag_id),
  FOREIGN KEY (gift_certificate_id) REFERENCES GiftCertificates (id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES Tags (id) ON DELETE CASCADE

);

INSERT INTO GiftCertificates (name, description, price, duration, create_date, last_update_date)
VALUES
('Shiny','Valid in any store in Poland, for 12 months from the date of activation.',200.00,365,'2021-08-29T06:12:15.156','2021-11-21T11:22:35.236'),
('Burger Queen','Valid in any restaurant in Poland, for 3 months from the date of activation.',50.00,365,'2021-11-21T11:22:35.236','2022-01-19T02:33:17.006'),
('Warsaw museums','Valid at every facility of the National Museum in Warsaw',80.00,180,'2022-01-19T02:33:17.006','2022-06-09T09:12:15.166'),
('MagicCinema','Can be used to purchase tickets as well as purchase snacks and drinks at the cinema bar.',120.00,540,'2020-10-05T14:24:11.056','2021-08-29T06:12:15.156'),
('Shoes&Clothes','Valid in every Shoes&Clothes store in Poland.',150.00,180,'2022-06-09T09:12:15.166','2022-07-09T09:12:15.166');

INSERT INTO Tags (name)
VALUES
('clothes'),
('drinks'),
('food'),
('jewelry'),
('birthday'),
('wedding'),
('romantic'),
('trip'),
('valentines'),
('restaurant'),
('shopping'),
('history'),
('museum'),
('art'),
('cinema');

INSERT INTO GiftCertificatesTags (gift_certificate_id, tag_id)
VALUES
(1,4),
(1,5),
(1,11),
(2,2),
(2,3),
(2,10),
(3,12),
(3,13),
(3,14),
(4,2),
(4,3),
(4,5),
(4,7),
(4,9),
(4,15),
(5,1),
(5,4),
(5,11);
