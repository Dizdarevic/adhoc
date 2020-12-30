#!/usr/bin/bash

# TODO chron job to update this brew cask leaves or whatever

# fix file permissions on half installed machines
# sudo chown -R $(whoami) /usr/local/share/zsh /usr/local/share/zsh/site-functions

# random nice things
defaults write -g ApplePressAndHoldEnabled -bool false

# mac setup
xcode-select --install

/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
sh -c "$(curl -fsSL https://raw.githubusercontent.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
brew tap homebrew/cask-versions
brew update

# Copy zshrc into actual
cp -f .zshrc ~/.zshrc
source ~/.zshrc

# brew UI apps
brew install --cask \
    brave-browser \
    docker \
    google-chrome \
    google-cloud-sdk \
    insomnia \
    intellij-idea \
    iterm2 \
    adoptopenjdk \
    microsoft-teams \
    minikube \
    postman \
    shiftit \
    slack \
    spotify \
    sublime-text \
    visual-studio-code

# CLIs
brew install \
    docker \
    git \
    go \
    gradle \
    htop \
    jq \
    kubernetes-cli \
    kubernetes-helm \
    maven \
    node \
    openssl \
    postgresql \
    python \
    the_silver_searcher \
    tree \
    vegeta \
    wget \
    zsh


# fun but not needed
brew install \
    clojure \
    scala \
    kotlin \

# install serverless
npm install serverless -g

# golang dev tools
# if ssl issues appear, you can use 'go get -insecure <package>' at own risk
go get golang.org/x/tools/cmd/godoc
go get github.com/golang/lint/golint

# aws cli stuff via python
# if ssl issues appear, you can use 'pip3 install --trusted-host pypi.org --trusted-host files.pythonhosted.org <package>' at own risk
pip3 install awscli awsebcli
echo "export PATH=\"$(brew --prefix python)/libexec/bin:$PATH\"" >> ~/.zshrc

# Git and Dotfiles
git config --global user.name "Dizveloper"
git config --global user.email "dizdarevic1994@gmail.com"

open /Applications/ShiftIt.app

# set up workspaces in home directory
mkdir ~/workspace
mkdir -p ~/go/bin
mkdir -p ~/go/src/github.com/Dizdarevic
