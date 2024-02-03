FROM --platform=${TARGETPLATFORM} node:18

RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get upgrade -y

#
# install gcloud tools
#
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y \
     apt-transport-https \
     build-essential \
     ca-certificates \
     curl \
     cmake \
     git \
     git-crypt \
     gnupg \
     less \
     mandoc \
     openjdk-17-jdk \
     openssl \
     openssh-server \
     python3 \
     python3-pip \
     python3-venv \     
     sudo \
     uuid-runtime

RUN sed -i- -e 's/ALL=(ALL:ALL) ALL/ALL=(ALL:ALL) NOPASSWD:ALL/' /etc/sudoers
# RUN echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers
RUN usermod -a -G sudo node

RUN chown -R node:node /usr/local/share/.config
RUN chown -R node:node /usr/local/bin

USER node
WORKDIR /home/node

CMD bash
