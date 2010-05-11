DESCRIPTION = "OpenOffice.org Impress presentation viewer for Qt/E based Palmtop environments"
SECTION = "opie/applications"
PRIORITY = "optional"
LICENSE = "GPL"
HOMEPAGE = "http://poqetp.sourceforge.net/"
PR = "r2"

SRC_URI = "${SOURCEFORGE_MIRROR}/poqetp/poqetp_${PV}_src.tar.gz"
S = "${WORKDIR}/poqetp"

inherit palmtop

do_configure_prepend() {
	find . -name "Makefile"|xargs rm -f # force regenerating Makefiles
}

do_install() {
        install -d ${D}${palmtopdir}/bin \
        	   ${D}${palmtopdir}/apps/Applications \
        	   ${D}${palmtopdir}/pics
        install -D -m 0755 poqetp/poqetp ${D}${palmtopdir}/bin/poqetp
        install -D -m 0644 poqetp.desktop ${D}${palmtopdir}/apps/Applications/poqetp.desktop
        install -d ${D}${palmtopdir}/pics/poqetp
        cp -pPR pics/* ${D}${palmtopdir}/pics/poqetp/
}

SRC_URI[md5sum] = "b003614237445d23b512cb9571565524"
SRC_URI[sha256sum] = "4347a70fe84bd2ccecc90738fb121d28298cf0bfdb4963ceee088c56d3109899"
