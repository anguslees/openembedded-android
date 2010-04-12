DESCRIPTION = "Standard Gtk+ theme for the Openmoko framework, QVGA edition"
SECTION = "openmoko/base"
RCONFLICTS = "openmoko-theme-standard"
RPROVIDES = "openmoko-theme-standard-2"
SRCREV = "3425"
PV = "0.1.1+svnr${SRCPV}"
PR = "r1"

inherit openmoko2

SRC_URI = "svn://svn.openmoko.org/trunk/src/target/OM-2007.2/artwork/themes;module=openmoko-standard-2-qvga;proto=http"
S = "${WORKDIR}/openmoko-standard-2-qvga"

do_install() {
	find ${WORKDIR} -name ".svn" | xargs rm -rf
	install -d ${D}${datadir}/themes/openmoko-standard-2/gtk-2.0
	cp -fpPR ${S}/* ${D}${datadir}/themes/openmoko-standard-2/
	rm -rf ${D}${datadir}/themes/openmoko-standard-2/patches/
	
	install -d ${D}${sysconfdir}/gtk-2.0
	echo 'include "${datadir}/themes/openmoko-standard-2/gtk-2.0/gtkrc"' >> ${D}${sysconfdir}/gtk-2.0/gtkrc
}

CONFFILES_${PN} = "${sysconfdir}/gtk-2.0/gtkrc"

PACKAGE_ARCH = "all"
FILES_${PN} = "${datadir} ${sysconfdir}"
