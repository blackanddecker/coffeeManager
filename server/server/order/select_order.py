import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime

def select_order(request, connection):
    data = request.get_json()
    result =[]
    order_info =[]
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'start_date' not in data:
        return 'No \"start_date\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'end_date' not in data:
        return 'No \"end_date\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'shop_id' not in data:
        return 'No \"shop_id\"', status.HTTP_406_NOT_ACCEPTABLE

    if "tables" in data:
        tables = ','.join('\\\'' + e + '\\\'' for e in data["tables"])
    else:
        tables = ''
    if "employees" in data:
        employees = ','.join('\\\'' + e + '\\\'' for e in data["employees"])
    else:
        employees = ''
    if "products" in data:
        products = ','.join('\\\'' + e + '\\\'' for e in data["products"])
    else:
        products = ''

    if "status" in data:
        order_status = ','.join('\\\'' + e + '\\\'' for e in data["status"])
    else:
        order_status = ''
    #all are ids
    try:
        with connection.cursor() as cursor:
            sql = "CALL order_search('{}','{}',{},'{}','{}','{}','{}');".format(data['start_date'],\
                 data['end_date'], data['shop_id'],tables, order_status, employees, products )
            print(sql)
            cursor.execute(sql)
            orders = cursor.fetchall()
            print(orders)

            if len(orders)>0:
                for order in orders: 
                    excists = False
                    for i in order_info: 
                        if order['id'] == i['id']:
                            print("excists")
                            excists = True
                    if excists == False:
                        order_info.append(order)

                for order in order_info: 
                    sql = "SELECT o.id as o_info, ord.id as order_id, o.details , o.product_id , p.name\
                            FROM shopmanager.orderinfo o ,shopmanager.order ord, shopmanager.product p \
                            where o.order_id = ord.id and o.product_id = p.id and ord.id ={}".format(order['id'])
                    #print(sql)
                    cursor.execute(sql)
                    orderDetails = cursor.fetchall()
                    order['orderHistory']=orderDetails

            return order_info, status.HTTP_200_OK
    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    return result, status.HTTP_200_OK