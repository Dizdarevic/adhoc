#! /usr/bin/bash

# TODO chron job to update this brew cask leaves or whatever

# fix file permissions on half installed machines
# sudo chown -R $(whoami) /usr/local/share/zsh /usr/local/share/zsh/site-functions
xcode-select --install

/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
brew tap caskroom/versions
brew update

# brew UI apps
brew cask install \
    docker \
    iterm2 \
    google-chrome \
    firefox \
    intellij-idea \
    shiftit \
    spotify \
    sublime-text \
    flux \
    postman \
    insomnia \
    java8

# CLIs
brew install \
    clojure \
    docker \
    git \
    go \
    gradle \
    htop \
    jq \
    kafka \
    kubernetes-cli \
    leiningen \
    maven \
    nginx \
    postgresql \
    python \
    sbt \
    scala \
    the_silver_searcher \
    thefuck \
    tree \
    emacs \
    wget \
    yarn \
    zsh 
    
# golang dev tools
go get golang.org/x/tools/cmd/godoc
go get github.com/golang/lint/golint

# aws cli stuff via python
pip3 install awscli awsebcli
echo "export PATH=\"$(brew --prefix python)/libexec/bin:$PATH\"" >> ~/.zshrc

# node packages
yarn global add typescript

# Git and Dotfiles
git config --global user.name "Edvin Dizdarevic"
git config --global user.email "dizdarevic1994@gmail.com"

open /Applications/ShiftIt.app
