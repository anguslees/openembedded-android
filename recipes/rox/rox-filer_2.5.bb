DESCRIPTION = "File manager at the core of the ROX desktop"
HOMEPAGE = "http://rox.sf.net"
LICENSE = "GPL"
SECTION = "x11/applications"
DEPENDS = "gtk+ shared-mime-info"
RDEPENDS = "shared-mime-info"
PR = "r3"

SRC_URI = "${SOURCEFORGE_MIRROR}/rox/${P}.tar.bz2 \
           file://no-strip-objcopy.patch;striplevel=3"

inherit mime pkgconfig

S = "${WORKDIR}/${P}/ROX-Filer/src/"

do_compile() {
	../AppRun --compile
}

do_install() {
	install -d ${D}${bindir}
	install -m 755 ../ROX-Filer ${D}${bindir}

	install -d ${D}${datadir}/rox/Choices
	install -d ${D}${datadir}/rox/images
	install -d ${D}${datadir}/mime/packages
	install -d ${D}${datadir}/doc/rox/html
	install -d ${D}${mandir}/man1

	gzip -c9 ${WORKDIR}/${P}/rox.1 >${D}${mandir}/man1/rox.1.gz

	cp -r ${WORKDIR}/${P}/Choices ${D}${datadir}/rox
	rm -rf ${D}${datadir}rox/Choices/MIME-info/
	cp ${WORKDIR}/${P}/ROX-Filer/*.xml ${D}${datadir}/rox

	cp ${WORKDIR}/${P}/ROX-Filer/Help/{Changes,README*,TODO} ${D}${datadir}/doc/rox
	cp ${WORKDIR}/${P}/ROX-Filer/Help/*html ${D}${datadir}/doc/rox/html
	cp ${WORKDIR}/${P}/ROX-Filer/style.css ${D}${datadir}/doc/rox/html
	cp -r ${WORKDIR}/${P}/ROX-Filer/images ${D}${datadir}/rox
	cp -r ${WORKDIR}/${P}/ROX-Filer/ROX ${D}${datadir}/rox

#	cp ROX-Filer/ROX-Filer ${D}/usr/bin/rox
	cp ${WORKDIR}/${P}/ROX-Filer/.DirIcon ${D}${datadir}/rox/.DirIcon
	cp ${WORKDIR}/${P}/rox.xml ${D}${datadir}/mime/packages

 	for f in ${WORKDIR}/${P}/ROX-Filer/Messages/*.gmo;  do
 	    export ROXTMP=`basename $f .gmo` ;
 	    if [ $ROXTMP == "sp" ]; then
 		export ROXTMP="es" ;
 	    fi
 	    install -d ${D}${datadir}/locale/$ROXTMP/LC_MESSAGES;
 	    cp $f ${D}${datadir}/locale/$ROXTMP/LC_MESSAGES/rox.mo;
 	done
}

FILES_${PN} += "${datadir}/rox/ ${datadir}/mime/packages"

SRC_URI[md5sum] = "56e6a29f2dbdf11d6f4b74a3f03ff959"
SRC_URI[sha256sum] = "d91120d78e770c9c09822560dacb42fca641d092cbe52064ca7232f089062921"
