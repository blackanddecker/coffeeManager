CREATE DEFINER=`root`@`%` PROCEDURE `order_search`(
IN start_time datetime,
IN end_time datetime,
IN shop_id integer,
IN tables VARCHAR(255),
IN _status VARCHAR(255),
IN employees VARCHAR(255),
IN products VARCHAR(255)
)
BEGIN
SET @sql = CONCAT('
SELECT  o.id, o.table_id, o.employee_id, o.status, o.date_order
FROM shopmanager.`order` o
	INNER JOIN shopmanager.orderinfo oi ON oi.order_id = o.id
	INNER JOIN product p ON oi.product_id = p.id
	INNER JOIN employee e ON o.employee_id = e.id
	INNER JOIN shopmanager.`table` t ON o.table_id = t.id
WHERE ((o.date_order >=  \'',start_time,'\')
and   (o.date_order <=  \'',end_time,'\'))
and t.shop_id = ', shop_id,
(CASE WHEN tables != '' THEN (CONCAT(' and t.id IN (',tables,')')) ELSE ('') END),
(CASE WHEN employees != '' THEN (CONCAT(' and e.id IN (',employees,')')) ELSE ('') END),
(CASE WHEN _status != '' THEN (CONCAT(' and o.status IN (',_status,')')) ELSE ('') END),
(CASE WHEN products != '' THEN (CONCAT(' and p.id IN (',products,')')) ELSE ('') END),
' order by o.date_order DESC;');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END

/* call order_search("2014-10-28","2021-10-28",0,'',"'paid'",'','3'); */