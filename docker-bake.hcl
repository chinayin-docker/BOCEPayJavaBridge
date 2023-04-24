variable "version" {
  default = ""
}

variable "repo" {
  default = "chinayin/bocepay-java-bridge"
}

variable "registry" {
  default = "docker.io"
}

variable "repository" {
  default = "${registry}/${repo}"
}

function "platforms" {
  params = []
  result = ["linux/amd64","linux/arm64"]
}

target "_all_platforms" {
  platforms = platforms()
}

group "default" {
  targets = ["jdk11"]
}

target "jdk11" {
  inherits = ["_all_platforms"]
  context  = "."
  tags     = [
    "${repository}:latest",
    "${repository}:${version}",
  ]
}
