CREATE TABLE GiftCertificates (
  certificate_id SERIAL PRIMARY KEY,
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
  FOREIGN KEY (gift_certificate_id) REFERENCES GiftCertificates (certificate_id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES Tags (id) ON DELETE CASCADE

);
