from .dto import ChangeStatusDTO

def change_order_status_handler(connection, data: ChangeStatusDTO):
    cur = connection.cursor()

    cur.execute("UPDATE order_table SET order_status = %s WHERE id = %s", (data.status, data.orderId))
    connection.commit()
    cur.close()

    return "", 204