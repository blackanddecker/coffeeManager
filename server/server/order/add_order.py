import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime



def add_order(request, connection):
    data = request.get_json()
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'table_id' not in data:
        return 'No \"table_id\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'user_id' not in data:
        return 'No \"user_id\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'orderHistory' not in data:
        return 'No \"orderHistory\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'order_id' not in data:
        return 'No \"order_id\"', status.HTTP_406_NOT_ACCEPTABLE

    try: 
        with connection.cursor() as cursor:
            #if we add a new order
            if len(data['orderHistory']) > 0 : 
                order_id = data['order_id']
                if data['order_id'] == -1: 
                    print("Add new order")
                    sql = "SELECT max(id) as id FROM shopmanager.order"
                    cursor.execute(sql)
                    order_id = int(cursor.fetchall()[0]['id']) + 1
                    print("Max order_id: " , order_id)

                    sql = "INSERT INTO shopmanager.order(id, date_order, status, table_id , employee_id) VALUES\
                    ({}, '{}', '{}', {}, {});".format(order_id, datetime.datetime.now(), 'pending', data['table_id'], data['user_id'])
                    cursor.execute(sql)
                    connection.commit()

                #if the order excist and we just add another product
                for product in data['orderHistory']: 
                    print("Add order info")
                    sql = "SELECT max(id) as id FROM shopmanager.orderinfo"
                    cursor.execute(sql)
                    order_info_id = int(cursor.fetchall()[0]['id']) + 1
                    print("Max order_info_id: " , order_info_id)

                    sql = "INSERT INTO shopmanager.orderinfo(id, details , product_id , order_id) VALUES\
                    ({}, '{}', {}, {});".format(order_info_id, product['details'], product['product_id'], order_id)
                    cursor.execute(sql)
                    connection.commit()

                    #update available product 
                    sql = "SELECT available FROM shopmanager.product p\
                    WHERE p.shop_id={} AND p.id = {};".format(data['shop_id'], int(product['product_id']))
                    cursor.execute(sql)
                    result = cursor.fetchall()
                    available = result[0]['available'] -1 
                    print("2",)

                    sql = "UPDATE shopmanager.product SET available= {} WHERE id={};".format(available, product['product_id'])
                    print(sql)
                    cursor.execute(sql)
                    connection.commit()

                #update price
                sql = "SELECT SUM(p.price) FROM shopmanager.order o, shopmanager.product p , shopmanager.orderinfo i\
                WHERE p.id = i.product_id AND i.order_id = o.id AND o.id={} AND p.shop_id={};".format(order_id, data['shop_id'])
                print(sql)
                cursor.execute(sql)
                result = cursor.fetchall()
                price = result[0]['SUM(p.price)']
                print(price)
                if price is None: 
                    price =0

                sql = "UPDATE shopmanager.order SET price= {} WHERE id={};".format(price,order_id)
                print(sql)
                cursor.execute(sql)
                connection.commit()
                


    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR


    finally:
        connection.close()
    responce = 'OK'
    return responce, status.HTTP_200_OK