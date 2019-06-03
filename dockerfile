FROM node:6.10.2

ADD C:/Users/Imbaker1234/Desktop/Project1-MK2/2. Service


EXPOSE 9999

ENTRYPOINT [ "pm2-docker", "src/main/webapp/index.html"]
