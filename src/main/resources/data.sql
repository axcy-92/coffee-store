-- initial drinks
INSERT INTO drinks (name, price)
SELECT name, price
FROM (
    SELECT 'Black Coffee' AS name, 4 AS price
    UNION ALL
    SELECT 'Latte', 5
    UNION ALL
    SELECT 'Mocha', 6
    UNION ALL
    SELECT 'Tea', 3
) AS x
WHERE NOT EXISTS (SELECT * FROM drinks);

-- initial toppings
INSERT INTO toppings (name, price)
SELECT name, price
FROM (
    SELECT 'Milk' AS name, 2 AS price
    UNION ALL
    SELECT 'Hazelnut syrup', 3
    UNION ALL
    SELECT 'Chocolate sauce', 5
    UNION ALL
    SELECT 'Lemon', 2
) AS x
WHERE NOT EXISTS (SELECT * FROM toppings);