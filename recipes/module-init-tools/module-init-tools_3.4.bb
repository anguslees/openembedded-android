require module-init-tools.inc

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/utils/kernel/module-init-tools/module-init-tools-${PV}.tar.bz2 \
	   file://modutils_extension;apply=yes \
"

bindir = "/bin"
sbindir = "/sbin"

do_configure_prepend() {
	sed -i -e /MAN5\ =/d -e /MAN8\ =/d Makefile.am 
}

do_install() {
	autotools_do_install
	for f in bin/lsmod sbin/insmod sbin/rmmod sbin/modprobe sbin/modinfo sbin/depmod; do
		mv ${D}/$f ${D}/$f.26
	done
}

pkg_postinst_module-init-tools() {
#!/bin/sh
for f in sbin/insmod sbin/modprobe sbin/rmmod sbin/modinfo; do
bn=`basename $f`
   update-alternatives --install /$f $bn /$f.26 60
done
update-alternatives --install /bin/lsmod bin-lsmod /bin/lsmod.26 60
update-alternatives --install /sbin/lsmod lsmod /bin/lsmod.26 60
}

pkg_prerm_module-init-tools() {
#!/bin/sh
for f in sbin/insmod sbin/modprobe sbin/rmmod sbin/modinfo; do
bn=`basename $f`
   update-alternatives --remove $bn /$f.26
done
update-alternatives --remove bin-lsmod /bin/lsmod.26
update-alternatives --remove lsmod /bin/lsmod.26
}

pkg_postinst_module-init-tools-depmod() {
#!/bin/sh
update-alternatives --install /sbin/depmod depmod /sbin/depmod.26 60
}

pkg_prerm_module-init-tools-depmod() {
#!/bin/sh
update-alternatives --remove depmod /sbin/depmod.26
}

SRC_URI[md5sum] = "db6ac059e80e8dd4389dbe81ee61f3c6"
SRC_URI[sha256sum] = "96c7a1b313722a5203d7a6c4cb89f09ac186f8565bb1666a6053979072633d87"
