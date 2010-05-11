DESCRIPTION = "PuTTY is a free implementation of Telnet and SSH for Win32 and Unix platforms"
SECTION = "console/network"
LICENSE = "MIT"
#Remove the dash below when 0.58 changes in PV
SRCDATE = "20060814"
PV = "0.58+cvs-${SRCDATE}"

SRC_URI = "svn://svn.tartarus.org/sgt;module=putty \
	   file://lib.pro \
	   file://plink.pro \
	   file://pscp.pro \
	   file://psftp.pro"

TARGETS = "lib plink pscp psftp"

S = "${WORKDIR}/putty"

inherit qmake_base

do_configure() {
	for t in ${TARGETS}
	do
		install -m 0644 ${WORKDIR}/$t.pro .
	done
	cd charset && perl sbcsgen.pl
}

do_compile() {
	for t in ${TARGETS}
	do
		rm -f Makefile
		qmake -makefile -o Makefile -spec ${QMAKESPEC} $t.pro
		oe_runmake
	done
}

do_install() {
	install -d ${D}${libdir}
	oe_libinstall -so libputty ${D}${libdir}
	install -d ${D}${bindir}/
	install -m 0755 plink ${D}${bindir}/ssh
	install -m 0755 pscp ${D}${bindir}/scp
	install -m 0755 psftp ${D}${bindir}/sftp
}

