CREATE TABLE TQ_USER (
                           id SERIAL PRIMARY KEY,
                           username VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           role VARCHAR(255) NOT NULL
);

CREATE TABLE TQ_TOPIC (
                          id SERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          user_id BIGINT,
                          parentTopic BIGINT,
                          FOREIGN KEY (user_id) REFERENCES TQ_USER(id),
                          FOREIGN KEY (parentTopic) REFERENCES TQ_TOPIC(id)
);


CREATE TABLE TQ_IMAGE (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          filename VARCHAR(255) NOT NULL,
                          contentType VARCHAR(255) NOT NULL,
                          size BIGINT NOT NULL,
                          bytes BYTEA
);

CREATE TABLE TQ_QUESTION (
                             id SERIAL PRIMARY KEY,
                             text TEXT,
                             topic BIGINT,
                             image BIGINT,
                             user_id BIGINT,
                             FOREIGN KEY (topic) REFERENCES TQ_TOPIC(id),
                             FOREIGN KEY (image) REFERENCES TQ_IMAGE(id),
                             FOREIGN KEY (user_id) REFERENCES TQ_USER(id)
);

CREATE TABLE TQ_REPORT (
                           id SERIAL PRIMARY KEY,
                           title VARCHAR(255) NOT NULL,
                           description TEXT,
                           created_date TIMESTAMPTZ,
                           user_id BIGINT,
                           FOREIGN KEY (user_id) REFERENCES TQ_USER(id)
);

CREATE TABLE TQ_TEST (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         publish BOOLEAN NOT NULL,
                         user_id BIGINT,
                         FOREIGN KEY (user_id) REFERENCES TQ_USER(id)
);

CREATE TABLE TQ_TEST_QUESTION (
                                  id SERIAL PRIMARY KEY,
                                  question BIGINT,
                                  test BIGINT,
                                  FOREIGN KEY (question) REFERENCES TQ_QUESTION(id),
                                  FOREIGN KEY (test) REFERENCES TQ_TEST(id)
);