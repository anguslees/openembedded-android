DESCRIPTION = "Synergy - control multiple computers with one keyboard and mouse"
HOMEPAGE = "http://synergy2.sourceforge.net/"
LICENSE = "GPLv2"
SECTION = "x11/utils"
# NOTE: This depends on full x11, not diet-x11
DEPENDS = "libx11 libxtst"
PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/synergy2/synergy-${PV}.tar.gz \
           file://synergy-1.3.1+gcc-4.3.patch"

do_configure_prepend() {
	grep -l -- -Werror "${S}/"* | xargs sed -i 's:-Werror::'
}

inherit autotools 

SRC_URI[md5sum] = "a6e09d6b71cb217f23069980060abf27"
SRC_URI[sha256sum] = "4a15e26456498dda5f3aa7d688a4892e0c50394a2f01685136fbc954bacc9691"
