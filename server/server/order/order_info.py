import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime

def order_info(request, connection):
    data = request.get_json()
    try:
        with connection.cursor() as cursor:
            sql = "SELECT o.id as o_info, ord.id as order_id, o.details , o.product_id , p.name\
                    FROM shopmanager.orderinfo o ,shopmanager.order ord, shopmanager.product p \
                    where o.order_id = ord.id and o.product_id = p.id and ord.id ={}".format(data['order_id'])
            #print(sql)
            cursor.execute(sql)
            orders = cursor.fetchall()
            print(orders)
            return orders, status.HTTP_200_OK
    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    return [], status.HTTP_200_OK