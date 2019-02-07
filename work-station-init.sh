#! /usr/bin/bash

# TODO chron job to update this brew cask leaves or whatever

# fix file permissions on half installed machines
# sudo chown -R $(whoami) /usr/local/share/zsh /usr/local/share/zsh/site-functions

# random nice things
defaults write -g ApplePressAndHoldEnabled -bool false

# mac setup
xcode-select --install

/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
brew tap caskroom/versions
brew update

# brew UI apps
brew cask install \
    discord \
    docker \
    firefox \
    flux \
    google-chrome \
    insomnia \
    intellij-idea \
    iterm2 \
    java8 \
    postman \
    shiftit \
    spotify \
    sublime-text

# CLIs
brew install \
    clojure \
    docker \
    emacs \
    git \
    go \
    gradle \
    htop \
    jq \
    kafka \
    kubernetes-cli \
    kubernetes-helm \
    leiningen \
    maven \
    minikube \
    nginx \
    openssl \
    postgresql \
    python \
    sbt \
    scala \
    the_silver_searcher \
    thefuck \
    tree \
    vegeta \
    wget \
    yarn \
    zsh
    
# golang dev tools
go get golang.org/x/tools/cmd/godoc
go get github.com/golang/lint/golint

# aws cli stuff via python
pip3 install awscli awsebcli
echo "export PATH=\"$(brew --prefix python)/libexec/bin:$PATH\"" >> ~/.zshrc

# Git and Dotfiles
git config --global user.name "Dizdarevic"
git config --global user.email "dizdarevic1994@gmail.com"

# node packages
yarn global add typescript

open /Applications/ShiftIt.app
