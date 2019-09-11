import pymysql.cursors
import flask
from flask_api import status
import json 

def delete_product(product_id):

    connection = pymysql.connect(host='127.0.0.1',
                 user='root',
                 password='root',
                 db='mydb',
                 charset='utf8mb4',
                 cursorclass=pymysql.cursors.DictCursor)

    try:       
        with connection.cursor() as cursor:
            sql = "DELETE FROM mydb.product WHERE product.id={}".format(int(product_id))
            cursor.execute(sql)
            result = cursor.fetchall()
            print(result)
        connection.commit()
    except Exception as e: 
        print ("Internal Error ", e)
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    finally:
        connection.close()
    responce = 'OK'
    return responce, status.HTTP_200_OK