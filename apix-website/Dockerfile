FROM registry.sxc.sh/public/nginx:latest 

# COPY ./nginx.conf /etc/nginx/nginx.conf

MAINTAINER jiahu.peng@sinosdx.com
RUN  rm -rf /usr/share/nginx/html/*.html
COPY  ./Shanghai /etc/localtime
ADD  dist/ /usr/share/nginx/html/


