require samba-essential.inc
inherit update-rc.d

PR = "r7"

SRC_URI = "file://config-lfs.patch \
	   file://init-essential \
           file://quota.patch;striplevel=0 \
	   file://smb-essential.conf \
	   file://smb-essential-inactive.conf \
	   file://Managing-Samba.txt"

RCONFLICTS = "samba"

INITSCRIPT_NAME = "samba"
# No dependencies, goes in at level 20 (NOTE: take care with the
# level, later levels put the shutdown later too - see the links
# in rc6.d, the shutdown must precede network shutdown).
INITSCRIPT_PARAMS = "defaults"
CONFFILES_${PN} = "${sysconfdir}/samba/smb.conf"

# The file system settings --foodir=dirfoo and overridden unconditionally
# in the samba config by --with-foodir=dirfoo - even if the --with is not
# specified!  Fix that here.  Set the privatedir to /etc/samba/private.
EXTRA_OECONF += "\
	--with-configdir=${sysconfdir}/samba \
	--with-privatedir=${sysconfdir}/samba/private \
	--with-lockdir=${localstatedir}/lock \
	--with-piddir=${localstatedir}/run \
	--with-logfilebase=${localstatedir}/log \
	--with-libdir=${libdir} \
	--with-mandir=${mandir} \
	"

do_install_append() {
	install -d "${D}${localstatedir}/log"
	rm -f ${D}/sbin/mount.smbfs
	rmdir ${D}/sbin
	install -d "${D}${sysconfdir}/init.d"
	install -d "${D}${sysconfdir}/samba/private"

	install -c -m 755 ${WORKDIR}/init-essential ${D}${sysconfdir}/init.d/samba

	install -d "${D}${sysconfdir}/samba"
	install -d "${D}/usr/share/samba/help"

	install -m 0644 ${WORKDIR}/smb-essential-inactive.conf "${D}${sysconfdir}/samba/"
	install -m 0644 ${WORKDIR}/smb-essential.conf "${D}${sysconfdir}/samba/smb.conf"

	install -m 0644 ${WORKDIR}/Managing-Samba.txt  ${D}/usr/share/samba/help

}

do_configure_append() {
	distro_up="`echo "${DISTRO}" | awk '{printf("%s\n",toupper($0))}'`"

	cat ${WORKDIR}/smb-essential-inactive.conf | sed "s/MYWORKGROUP/${distro_up}/" > ${WORKDIR}/smb-essential-inactive.conf_
	mv  ${WORKDIR}/smb-essential-inactive.conf_ ${WORKDIR}/smb-essential-inactive.conf

	cat ${WORKDIR}/smb-essential.conf | sed "s/MYWORKGROUP/${distro_up}/" > ${WORKDIR}/smb-essential.conf_
	mv  ${WORKDIR}/smb-essential.conf_ ${WORKDIR}/smb-essential.conf
}

FILES_${PN} = "${bindir}/smbpasswd \
	       ${sbindir}/nmbd \
	       ${sbindir}/smbd \
	       ${libdir}/charset \
	       ${libdir}/vfs \
	       ${libdir}/*.dat \
	       /usr/share/samba \
	       /etc"
