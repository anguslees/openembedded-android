DEPENDS = "libgpewidget sqlite"
RDEPENDS = "gpe-icons"
LICENSE = "GPL"
DESCRIPTION = "A GPE notebook to sketch your notes"
export CVSBUILD = "no"

PR = "r1"

inherit gpe pkgconfig

CFLAGS +="-D_GNU_SOURCE"

SRC_URI = "${GPE_MIRROR}/${PN}-${PV}.tar.gz"

FILES_${PN} = "${bindir} ${datadir}/pixmaps ${datadir}/applications"
FILES_${PN} += " ${datadir}/gpe/pixmaps"

do_compile () {
        oe_runmake PREFIX=${prefix}
        oe_runmake all-desktop PREFIX=${prefix}
}

do_install () {
        oe_runmake PREFIX=${prefix} DESTDIR=${D} install-program
}

SRC_URI[md5sum] = "e95606bf33779b67acbfd626ad80000f"
SRC_URI[sha256sum] = "dc0648a0f1eabbf42a5ec8d04e02a576097b68cc1ce0558546d9081cd89b5e1b"
