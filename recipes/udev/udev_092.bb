DESCRIPTION = "udev is a daemon which dynamically creates and removes device nodes from \
/dev/, handles hotplug events and loads drivers at boot time. It replaces \
the hotplug package and requires a kernel not older than 2.6.12."
RPROVIDES_${PN} = "hotplug"

require udev.inc

LD = "${CC}"

PR = "${INC_PR}.0"

SRC_URI += "file://noasmlinkage.patch \
	    file://flags.patch \
	    file://udevsynthesize.patch \
	    file://udevsynthesize.sh \
            file://arm_inotify_fix.patch \
	    file://mtd-exclude-persistent.patch \
	    file://mount.blacklist \
	   "

FILES_${PN} += "${base_libdir}/udev/*"
FILES_${PN}-dbg += "${base_libdir}/udev/.debug"
UDEV_EXTRAS = "extras/firmware/ extras/scsi_id/ extras/volume_id/ extras/run_directory/"
EXTRA_OEMAKE += "libudevdir=/lib/udev libdir=${base_libdir} prefix="

do_install () {
	install -d ${D}${usrsbindir} \
		   ${D}${sbindir} \
		   ${D}${sysconfdir}
	oe_runmake 'DESTDIR=${D}' INSTALL=install install
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/udev

	install -d ${D}${sysconfdir}/udev/rules.d/

	install -m 0644 ${WORKDIR}/mount.blacklist     ${D}${sysconfdir}/udev/
	install -m 0644 ${WORKDIR}/local.rules         ${D}${sysconfdir}/udev/rules.d/local.rules
	install -m 0644 ${WORKDIR}/permissions.rules   ${D}${sysconfdir}/udev/rules.d/permissions.rules
	install -m 0644 ${WORKDIR}/udev.rules          ${D}${sysconfdir}/udev/rules.d/udev.rules
	install -m 0644 ${WORKDIR}/links.conf          ${D}${sysconfdir}/udev/links.conf
	if [ "${UDEV_DEVFS_RULES}" = "1" ]; then
		install -m 0644 ${WORKDIR}/devfs-udev.rules ${D}${sysconfdir}/udev/rules.d/devfs-udev.rules
	fi

	install -d ${D}${sysconfdir}/udev/scripts/

	install -m 0755 ${WORKDIR}/mount.sh ${D}${sysconfdir}/udev/scripts/mount.sh
	install -m 0755 ${WORKDIR}/network.sh ${D}${sysconfdir}/udev/scripts

	install -d ${D}${base_libdir}/udev/
	install -m 0755 ${S}/udevsynthesize ${D}${base_libdir}/udev/udevsynthesize
	install -m 0755 ${WORKDIR}/udevsynthesize.sh ${D}${sbindir}/udevsynthesize
}

pkg_postinst_append() {

	# Add the root partition to mount.blacklist to avoid a bug in the auto-mounter,
	# causing confusion with fsck on boot

        while read dev mp fs junk
        do
                if test "$mp" = "/"
                then
                        root_partition="$dev"
                        echo "$root_partition" >> $D${sysconfdir}/udev/mount.blacklist
                fi
        done < $D${sysconfdir}/fstab

}


SRC_URI[md5sum] = "a3954a4fc25cee4e1f6df498de8f72c7"
SRC_URI[sha256sum] = "e402e316ffddbdd5312d3a5957fccf47de7a8b62c7bb3710829a2e696e3818d1"
