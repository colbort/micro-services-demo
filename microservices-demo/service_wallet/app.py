import logging

from robyn import Robyn

from communication.mq_consumer import MQConsumer
from config import config as config
from config.settings import *
from handler.db_handler import DBHandler
from handler.redis_handler import RedisHandler
from handler.router_instance import router
from handler.user_cache_handler import userCache
from nacos_client.nacos_client import NacosWrapper
from router.wallet_router import setup_routes

# 初始化日志记录器
logger = logging.getLogger("robyn")
logger.setLevel(logging.INFO)


# logger = logging.getLogger("pika")
# logger.setLevel(logging.INFO)


def __init_config() -> NacosWrapper:
    nanosClient = NacosWrapper(
        server_address=SERVER_ADDRESS,
        username=USERNAME,
        password=PASSWORD,
        namespace=NAMESPACE,
    )
    config.config = config.Config(nanosClient, DATA_ID, GROUP)
    return nanosClient


def __init_rabbitmq():
    consumer = MQConsumer(
        host=config.Rabbitmq.host(),
        port=config.Rabbitmq.port(),
        username=config.Rabbitmq.username(),
        password=config.Rabbitmq.password(),
        queue_name=config.Wallet.serviceChannel(),
        router=router,
    )
    from threading import Thread
    Thread(target=consumer.start).start()


async def __init_services():
    # 初始化 MYSQL
    await DBHandler.init(config.Mysql.write(), config.Mysql.read())
    # 初始化 Redis
    await RedisHandler.init(
        host=config.Redis.host(),
        port=config.Redis.port(),
        db=config.Redis.db(),
        password=config.Redis.password()
    )
    # 初始化内存缓存
    await userCache.init_cache_handler()


if __name__ == "__main__":
    # 初始化配置
    client = __init_config()
    # 服务注册
    client.register_service(
        service_name=config.Wallet.serviceName(),
        port=config.Wallet.servicePort(),
    )
    # 启动 MQ 消费
    __init_rabbitmq()
    # 启动 HTTP 服务
    app = Robyn(__file__)
    app.startup_handler(__init_services)
    setup_routes(app)
    app.start(host="0.0.0.0", port=config.Wallet.servicePort())
