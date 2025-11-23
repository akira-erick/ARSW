import os
from flask import Flask

app = Flask(__name__)

@app.route('/order', methods=['POST'])
def make_order():
    return "Order service is running!"

@app.route('/order/compensate', methods=['POST'])
def make_order_compensation():
    return "Order compensation executed!"

if __name__ == '__main__':
    port = int(os.getenv('HOST_PORT_ORDER', 8081))
    app.run(debug=True, host='0.0.0.0', port = port)