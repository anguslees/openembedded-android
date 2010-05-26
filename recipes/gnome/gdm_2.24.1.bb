DESCRIPTION = "Graphical login manager"
LICENSE = "GPL"

DEPENDS = "grep consolekit libpam gnome-doc-utils gtk+ libglade libgnomecanvas librsvg libxml2 libart-lgpl"

PR = "r3"

inherit gnome update-rc.d

SRC_URI += "file://cross-xdetection.diff \
            file://%gconf-tree.xml \
            file://gdm.conf \
            file://gdm \
           "

EXTRA_OECONF = " --enable-authentication-scheme=shadow \
                 --with-console-kit \
                 --disable-scrollkeeper "

do_install_prepend() {
	mkdir -p ${D}/var/lib/gdm/.gconf.mandatory
	cp ${WORKDIR}/%gconf-tree.xml ${D}/var/lib/gdm/.gconf.mandatory/ 
	install -d ${D}/${sysconfdir}/gdm
	install -m 0644 ${WORKDIR}/gdm.conf ${D}/${sysconfdir}/gdm/
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/gdm ${D}/${sysconfdir}/init.d/
}

FILES_${PN} += "${datadir}/icon* \
		${datadir}/xsession*"

CONFFILES_${PN} += "${sysconfdir}/gdm/gdm.conf ${sysconfdir}/init.d/gdm"

INITSCRIPT_NAME = "gdm"
INITSCRIPT_PARAMS = "start 99 5 2 . stop 20 0 1 6 ."

pkg_postinst_${PN} () {
    # can't do this offline
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    grep "^gdm:" /etc/group > /dev/null || addgroup gdm
    grep "^gdm:" /etc/passwd > /dev/null || adduser --disabled-password --system --home /var/lib/gdm gdm --ingroup gdm -g gdm

if [ -d /var/lib/gdm ]; then
  chown -R gdm:gdm /var/lib/gdm
  chmod 0750 /var/lib/gdm
fi

# Register up as default dm
mkdir -p ${sysconfdir}/X11/
echo "${bindir}/gdm" > ${sysconfdir}/X11/default-display-manager

}

pkg_postrm_${PN} () {
    deluser gdm || true
    delgroup gdm || true
	sed -i /gdm/d ${sysconfdir}/X11/default-display-manager || true
}





SRC_URI[archive.md5sum] = "31139d7a79096463b127b4790058b056"
SRC_URI[archive.sha256sum] = "8869f464818a1626e481bba5c562bdb972ffb74f1a9da91d01199fa860ee040f"
