import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime



def update_delivered_order(request, connection):
    data = request.get_json()
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'order_id' not in data:
        return 'No \"order_id\"', status.HTTP_406_NOT_ACCEPTABLE

    try: 
        with connection.cursor() as cursor:
            sql = "SELECT SUM(p.price) FROM shopmanager.order o, shopmanager.product p , shopmanager.orderinfo i\
            WHERE p.id = i.product_id AND i.order_id = o.id AND o.id={};".format(data['order_id'])
            print(sql)
            cursor.execute(sql)
            result = cursor.fetchall()
            price = result[0]['SUM(p.price)']

            if price is None: 
                price =0 
        with connection.cursor() as cursor:
            sql = "UPDATE shopmanager.order SET price= {}, status='delivered', date_delivered='{}' WHERE id={};".format(price , datetime.datetime.now(),data['order_id'])
            cursor.execute(sql)
            connection.commit()

    except Exception as e: 
        print ("Internal Error ", e)
        return {"success":False}, status.HTTP_500_INTERNAL_SERVER_ERROR

    finally:
        connection.close()
    responce = 'OK'
    return {"success":True}, status.HTTP_200_OK