import pymysql.cursors
import flask
from flask_api import status
import json 


def show_tables(shop_id , connection):
    try:       
        with connection.cursor() as cursor:
            sql = "SELECT shop_id, no_table, id as table_id FROM shopmanager.table t WHERE t.shop_id ={}".format(shop_id)
            print(sql)
            cursor.execute(sql)
            result = cursor.fetchall()
            print(result)
    except Exception as e:
        print(e) 
        return 'Internal Server Error', status.HTTP_500_INTERNAL_SERVER_ERROR

    finally:
        connection.close()
    return result, status.HTTP_200_OK