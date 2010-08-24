DESCRIPTION = "'repo' tool from Android"
LICENSE = "Apache"
SECTION = "devel"
PRIORITY = "optional"
PACKAGE_ARCH = "all"

SRC_URI = "http://android.git.kernel.org/repo"
S = "${WORKDIR}"

RDEPENDS_${PN} += "git python"

BBCLASSEXTEND = "native sdk"

NATIVE_INSTALL_WORKS = 1

do_compile() {
	:
}

do_install() {
	install -d ${D}${bindir}
	install -m 775 ${S}/repo ${D}${bindir}
}

SRC_URI[md5sum] = "2d1a8f64006dda271f6ba31f3b44ae86"
SRC_URI[sha256sum] = "ea3d3505ad58868afd9c3d4ef4747962891666166360f5d06022ac687f8beb5a"
