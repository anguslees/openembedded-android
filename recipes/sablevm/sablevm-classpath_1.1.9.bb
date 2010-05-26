DESCRIPTION = "GNU Classpath modified to work with SableVM"
HOMEPAGE = "http://sablevm.org"
LICENSE = "Classpath"
PRIORITY = "optional"
SECTION = "libs"
PR = "r1"

DEPENDS = "glib-2.0 gtk+ libart-lgpl pango libxtst virtual/javac-native fastjar-native"
RDEPENDS_${PN} = "${PN}-native"

SRC_URI = "http://sablevm.org/download/release/${PV}/${PN}-${PV}.tar.gz \
           file://disable-automake-checks.patch"

inherit autotools

EXTRA_OECONF = "--enable-compressed-classes=fastjar"

PACKAGES += " ${PN}-native"

FILES_${PN} = "${datadir}/${PN}"
FILES_${PN}-native = "${libdir}/${PN}"
