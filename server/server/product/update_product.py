import pymysql.cursors
import flask
from flask_api import status
import json 
import datetime

def update_product(request , connection):
    data = request.get_json()
    print(data)
    if request.method != 'POST':
        return '', status.HTTP_406_NOT_ACCEPTABLE
    if 'product_id' not in data:
        return 'No \"product_id\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'price' not in data:
        return 'No \"price\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'name' not in data:
        return 'No \"name\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'available' not in data:
        return 'No \"available\"', status.HTTP_406_NOT_ACCEPTABLE
    if 'details' not in data:
        return 'No \"details\"', status.HTTP_406_NOT_ACCEPTABLE

    try:
        with connection.cursor() as cursor:
            sql = "UPDATE shopmanager.product SET name='{}', price={}, details='{}', available = {} WHERE id={}\
            ".format(data['name'],data['price'],data['details'], data['available'], data['product_id'])
            cursor.execute(sql)
            connection.commit()
        responce = 'OK'
        return responce, status.HTTP_200_OK
    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

