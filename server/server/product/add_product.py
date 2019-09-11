import pymysql.cursors
import flask
from flask_api import status
import json 


def add_product(request, connection):
    data = request.get_json()
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'price' not in data:
        return 'No \"price\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'name' not in data:
        return 'No \"name\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'details' not in data:
        return 'No \"details\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'shop_id' not in data:
        return 'No \"shop_id\"', status.HTTP_406_NOT_ACCEPTABLE

    try:       
        ## Find max id from DB
        with connection.cursor() as cursor:
            sql = "SELECT max(id) as id FROM shopmanager.product"
            cursor.execute(sql)
            product_id = int(cursor.fetchall()[0]['id']) + 1
            print("Max product_id: " , product_id)
            sql = "INSERT INTO shopmanager.product (id, name, price,  details, shop_id , available)\
             VALUES ({}, '{}', {}, '{}', {}, {});".format(
                    product_id, str(data['name']), float(data['price']),str(data['details']),int(data['shop_id']) ,int(data['available']))
            cursor.execute(sql)
            connection.commit()
    except Exception as e: 
        print(e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    responce = 'OK'
    return responce, status.HTTP_200_OK