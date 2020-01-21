import flask
from flask_httpauth import HTTPBasicAuth
from flask_cors import CORS
import pymysql.cursors
from flask import Flask, render_template, request, jsonify, url_for, redirect

from server import login_user
from server import login_mobile

#shops
from server.shop import addShop
from server.shop import selectShop
#employees
from server.user import addUser
from server.user import selectUser
from server.user import updateUser
#products
from server.product import add_product
from server.product import select_product
from server.product import update_product
#tables
from server.table import add_table
from server.table import select_table
from server.table import show_tables

#orders
from server.order import add_order
from server.order import select_order
from server.order import update_order
from server.order import update_paid_order
from server.order import update_delivered_order



app = Flask(__name__)
cors = CORS(app, resorces={r'/*': {"origins": '*'}})
app.config['CORS_HEADER'] = 'Content-Type'
auth = HTTPBasicAuth()


def get_connection():
    connection = pymysql.connect(host='127.0.0.1',
    #connection = pymysql.connect(host='35.228.7.51',
             user='root',
             password='root',
             db='shopmanager',
             charset='utf8mb4',
             cursorclass=pymysql.cursors.DictCursor)
    
    return connection


@auth.get_password
def get_password(username):
    if username == 'shop':
        return 'password'
    return None

@auth.error_handler
def unauthorized():
    return make_response(jsonify({'error': 'Unauthorized access'}), 401)

@app.route('/')
def login():
    #return render_template("index.html", message="Hello Flask!"); 
    print("in login") 
    return render_template('login.html')



@app.route('/login_user', methods=['POST'])
#@auth.login_required
def _login_user():
    connection = get_connection()
    content , status = login_user.login_user(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    print(status)
    return response

@app.route('/login_mobile', methods=['POST'])
#@auth.login_required
def _login_mobile():
    connection = get_connection()
    content , status = login_mobile.login_mobile(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    print(status)
    return response

@app.route('/orders')
def orders():
    return render_template('orders.html')

@app.route('/products')
def products():
    return render_template('products.html')

@app.route('/contact')
def contact():
    return render_template('contact.html')

@app.route('/index')
def index():
    return render_template('index.html')

@app.route('/users')
def users():
    return render_template('users.html')

@app.route('/tables')
def tables():
    return render_template('tables.html')


#-------------------------------------------
@app.route('/script')
def _script():
    return render_template('script.js')

@app.route('/usersjs')
def _userjs():
    return render_template('usersjs.js')

@app.route('/ordersjs')
def _ordersjs():
    return render_template('ordersjs.js')



















#------------------------------------------------------------------_Shop
@app.route('/addShop', methods=['POST'])
#@auth.login_required
def _addShop():
    connection = get_connection()
    content , status = addShop.addShop(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

@app.route('/selectShop', methods=['GET'])
#@auth.login_required
def _selectShop():
    connection = get_connection()
    content , status = selectShop.selectShop(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response


#--------------------------------------------------------------------_User
@app.route('/addUser', methods=['POST'])
#@auth.login_required
def _addUser():
    connection = get_connection()
    content , status = addUser.addUser(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response


@app.route('/selectUser/<shop_id>', methods=['GET'])
#@auth.login_required
def _selectUser(shop_id):
    connection = get_connection()
    content , status = selectUser.selectUser(shop_id, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

@app.route('/updateUser', methods=['POST'])
#@auth.login_required
def _updateUser():
    connection = get_connection()
    content , status = updateUser.updateUser(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response


#--------------------------------------------------------------------_Product
@app.route('/add_product', methods=['POST'])
#@auth.login_required
def _add_product():
    connection = get_connection()
    content , status = add_product.add_product(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

@app.route('/select_product/<shop_id>', methods=['GET'])
#@auth.login_required
def _select_product(shop_id):
    connection = get_connection()
    content , status = select_product.select_product(shop_id, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

@app.route('/update_product', methods=['POST'])
#@auth.login_required
def _update_product():
    connection = get_connection()
    content , status = update_product.update_product(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response


#--------------------------------------------------------------------_Tables
@app.route('/add_table', methods=['POST'])
#@auth.login_required
def _add_table():
    connection = get_connection()
    content , status = add_table.add_table(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

@app.route('/select_table/<shop_id>', methods=['GET'])
#@auth.login_required
def _select_table(shop_id):
    connection = get_connection()
    content , status = select_table.select_table(shop_id, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

@app.route('/show_tables/<shop_id>', methods=['GET'])
#@auth.login_required
def _show_tables(shop_id):
    connection = get_connection()
    content , status = show_tables.show_tables(shop_id, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response
#--------------------------------------------------------------------_Order




@app.route('/add_order', methods=['POST'])
#@auth.login_required
def _add_order():
    connection = get_connection()
    content , status = add_order.add_order(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response


@app.route('/select_order', methods=['POST'])
#@auth.login_required
def _select_order():
    connection = get_connection()
    content , status = select_order.select_order(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

@app.route('/update_order', methods=['POST'])
#@auth.login_required
def _update_order():
    connection = get_connection()
    content , status = update_order.update_order(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response


@app.route('/update_delivered_order', methods=['POST'])
#@auth.login_required
def _update_delivered_order():
    connection = get_connection()
    content , status = update_delivered_order.update_delivered_order(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

@app.route('/update_paid_order', methods=['POST'])
#@auth.login_required
def _update_paid_order():
    connection = get_connection()
    content , status = update_paid_order.update_paid_order(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response

'''















@app.route('/login', methods=['POST'])
#@auth.login_required
def _login():
    connection = get_connection()
    content , status = login.login(request, connection)
    response = flask.jsonify(content)
    response.status_code = status
    return response









@app.route('/delete_product/<product_id>', methods=['GET'])
#@auth.login_required
def _delete_product(product_id):
    print(product_id)
    content , status = delete_product.delete_product(product_id)
    response = flask.jsonify(content)
    response.status_code = status
    return response


#################################################
#               Add order                       #
#################################################
@app.route('/add_order', methods=['POST'])
@auth.login_required
def _add_order():
    content , status = add_order.add_order(request)
    response = flask.jsonify(content)
    response.status_code = status
    return response
#################################################
#               Update order                    #
#################################################
@app.route('/update_order', methods=['POST'])
@auth.login_required
def _update_order():
    content , status = update_order.update_order(request)
    response = flask.jsonify(content)
    response.status_code = status
    return response
#################################################
#               Search order                    #
#################################################
@app.route('/select_order', methods=['POST'])
#@auth.login_required
def _select_order():
    content , status = select_order.select_order(request)
    response = flask.jsonify(content)
    response.status_code = status
    return response


'''
if __name__ == "__main__":
    #app.run()
    #app = create_app()
    app.run(host='0.0.0.0', port=5000, debug=True)