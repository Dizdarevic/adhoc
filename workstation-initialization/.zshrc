export PATH=/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/go/bin:/opt/X11/bin:/usr/local/Cellar/gradle/5.1.1/bin
export PATH=${PATH}:${HOME}/.npm-global/bin:${HOME}/.npm-global/lib/node_modules:
# Add Visual Studio Code (code)
export PATH="$PATH:/Applications/Visual Studio Code.app/Contents/Resources/app/bin"

export WORKSPACE_DIR=$HOME/workspace

# node
export NODE_PATH=${HOME}/.npm-global/lib/node_modules

# java
export JAVA_HOME=/Library/Java/Home

# golang
export HOME=${HOME}
export GOPATH=$HOME/go

# to run vscode as root user because of EACCES issue with go extension
alias vscode="sudo code . --user-data-dir='.'"

# Path to your oh-my-zsh installation.
export ZSH=${HOME}/.oh-my-zsh

ZSH_THEME="robbyrussell"

# Which plugins would you like to load? (plugins can be found in ~/.oh-my-zsh/plugins/*)
# Custom plugins may be added to ~/.oh-my-zsh/custom/plugins/
# Example format: plugins=(rails git textmate ruby lighthouse)
# Add wisely, as too many plugins slow down shell startup.
plugins=(
  git
  # ubuntu
  python
  pylint
  pip
  # oc
  nvm
  npm
  node
  mvn
  gradle
  iterm2
  helm
  golang
  gcloud
  docker
  aws
  bazel
  terraform
  ruby
  spring
  vscode
)

source $ZSH/oh-my-zsh.sh

# The next line updates PATH for the Google Cloud SDK.
if [ -f "${WORKSPACE_DIR}/google-cloud-sdk/path.zsh.inc" ]; then . "${WORKSPACE_DIR}/google-cloud-sdk/path.zsh.inc"; fi

# The next line enables shell command completion for gcloud.
if [ -f "${WORKSPACE_DIR}/google-cloud-sdk/completion.zsh.inc" ]; then . "${WORKSPACE_DIR}/google-cloud-sdk/completion.zsh.inc"; fi

