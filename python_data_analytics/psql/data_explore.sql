-- Show first 10 rows
SELECT * FROM retail LIMIT 10;

-- Check # of records
SELECT * FROM retail LIMIT 10;

-- number of clients (e.g. unique client ID)
SELECT COUNT(DISTINCT customer_id) AS num_clients FROM retail;

--invoice date range
SELECT MAX(invoice_date) AS max, MIN(invoice_date) AS min FROM retail;

-- number of SKU/merchants (e.g. unique stock code)
SELECT COUNT(DISTINCT stock_code) AS num_sku FROM retail;

--Calculate average invoice amount excluding invoices with a negative amount.
--method1
SELECT SUM(unit_price*quantity)/COUNT(DISTINCT invoice_no) AS avg
FROM retail 
WHERE quantity > 0 AND unit_price > 0;

--method2
WITH tmp AS (SELECT invoice_no,
                    count(*) AS num_per_invoice,
                    SUM(unit_price * quantity) AS total_amount_per_invoice
             FROM retail
             WHERE quantity > 0 AND unit_price > 0
             GROUP BY invoice_no
             --or HAVING SUM(unit_price * quantity)>0
             ORDER BY invoice_no
)
SELECT AVG(total_amount_per_invoice) AS avg FROM tmp;

--Calculate total revenue(p.s. canceled orders also been considered)
SELECT SUM(unit_price*quantity) FROM retail;

--Calculate total revenue by YYYYMM (quantity*unit_price<0 also been considered)
--create a funtion to convert the invoice_date into specific form
CREATE FUNCTION convert_time(ts timestamp) RETURNS int AS
27924
BEGIN
    RETURN EXTRACT(YEAR FROM ts)::int * 100 + EXTRACT(MONTH FROM ts)::int;
END;
27924
    LANGUAGE PLPGSQL;

SELECT convert_time(invoice_date) as yyyymm,
       SUM(unit_price*quantity) as sum
FROM retail
GROUP BY yyyymm
ORDER BY yyyymm;


