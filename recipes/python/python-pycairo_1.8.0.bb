DESCRIPTION = "Python Bindings for the Cairo canvas library"
SECTION = "devel/python"
HOMEPAGE = "http://cairographics.org/pycairo"
LICENSE = "LGPL MPL"
# cairo >= 1.8.0
DEPENDS = "cairo"
PR = "ml0"

DEFAULT_PREFERENCE = "-1"

SRC_URI = "http://cairographics.org/releases/pycairo-${PV}.tar.gz \
           file://fix-setup-py.patch"
S = "${WORKDIR}/pycairo-${PV}"

inherit distutils pkgconfig

do_configure_append() {
	sed -e 's:@prefix@:${prefix}:' -e 's:@includedir@:\$\{prefix\}/include:' -e 's:@VERSION@:${PV}:' pycairo.pc.in > pycairo.pc
}

do_stage() {
	install -d ${STAGING_INCDIR}
	install -m 0644 cairo/pycairo.h ${STAGING_INCDIR}
}

SRC_URI[md5sum] = "847f5377c32228a656819f5bd18eb6b4"
SRC_URI[sha256sum] = "a2272dafceb25ed9fedd0b10429dfec9c412b3b36eb6137f79c87e7d004036d4"
