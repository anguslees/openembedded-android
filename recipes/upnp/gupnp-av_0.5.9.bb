LICENSE = "LGPL"
DEPENDS = "gupnp"

SRC_URI = "http://gupnp.org/sites/all/files/sources/gupnp-av-${PV}.tar.gz \
"
SRC_URI[md5sum] = "5940df3c1152894685c3fc38ee95fd78"
SRC_URI[sha256sum] = "a909129997f79dcb6d35221ce205854d64c47a7390843e420cfba753485087bd"

inherit autotools pkgconfig
