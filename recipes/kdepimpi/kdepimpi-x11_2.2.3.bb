SRC_URI = "${SOURCEFORGE_MIRROR}/kdepimpi/kdepimpi-${PV}.tar.gz \
file://libkcal.patch \
file://kabc.patch \
file://kammu.patch \
file://korganizer.patch \
file://nomail.patch \
"

include kdepimpi-base.inc
include kdepimpi-x11.inc

PR = "r1"

SRC_URI[md5sum] = "b452510c392d852c7a94bb08547f4e9a"
SRC_URI[sha256sum] = "d6b21f0dad8277925abbb15d3c9cbc0a58af5b5119bbd1fde8637b600bae0489"
