WITH names AS (
    SELECT generate_series(1, 100000) AS id,
           substring(md5(random()::text), 1, 10) AS name
)

INSERT INTO offer (offer_id, exposable, client_fio)
SELECT id, (id % 2),
       translate(substring(name, 1, 20), '0123456789', 'abcdefghij')
FROM names;

###
DELETE FROM offer;