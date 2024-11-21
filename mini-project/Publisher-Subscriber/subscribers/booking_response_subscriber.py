import pika
import json
from utilities import get_status_from_db

class BookingResponseSubscriber:
    def __init__(self, exchange_name, queue_name):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
        self.channel = self.connection.channel()
        self.exchange_name = exchange_name
        self.queue_name = queue_name
        self.channel.exchange_declare(exchange=exchange_name, exchange_type='fanout')
        self.channel.queue_declare(queue=self.queue_name)
        self.channel.queue_bind(exchange=exchange_name, queue=self.queue_name)
        self.channel.basic_qos(prefetch_count=1)
         

    def callback(self, ch, method, properties, body):
        print(" [y] Checking status for request_id:", json.loads(body)['request_id'])
        request_id = json.loads(body)['request_id']
        status = get_status_from_db(request_id)
        print(f" [y] Status for request_id {request_id}: {status}")
        # Publish it on message exchange
        self.channel.basic_publish(exchange='message_exchange',
                        routing_key='',
                        body=json.dumps({"request_id": request_id, "status": status}))
        return status
        
    def start_consuming(self):
        self.channel.basic_consume(queue=self.queue_name, on_message_callback=self.callback, auto_ack=True)
        print(' [^] Waiting for messages. To exit press CTRL+C')
        self.channel.start_consuming()

    def close_connection(self):
        self.connection.close()