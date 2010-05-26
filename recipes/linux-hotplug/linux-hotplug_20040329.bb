SECTION = "base"
DESCRIPTION = "This package contains the scripts necessary \
for hotplug Linux support, and lets you plug in new devices \
and use them immediately."
LICENSE = "GPL"
RPROVIDES_${PN} = "hotplug"
RCONFLICTS_${PN} = "hotplug"
RREPLACES_${PN} = "hotplug"
PR = "r1"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/utils/kernel/hotplug/hotplug-2004_03_29.tar.gz \
	   file://busybox.patch \
           file://fix-net.agent \
           file://update-usb.usermap \
           file://logcheck-ignore \
	   file://sleeve.agent file://sleeve.rc file://mmc.agent \
	   file://usbd.agent"
S = "${WORKDIR}/hotplug-2004_03_29"

INITSCRIPT_NAME = "hotplug"
INITSCRIPT_PARAMS = "start 40 S . stop 89 0 6 ."

inherit update-rc.d

do_compile () {
	:
}

oldmandir := "${mandir}"
oldsbindir := "${sbindir}"
prefix = ""
exec_prefix = ""
FILES_hotplug_append = " ${oldsbindir}"
FILES_hotplug-doc_append = " ${oldmandir}"

export DEBFIX = "sed -e 's:sysconfig/usb:default/hotplug.usb:'"
do_install () {
	install -d ${D}${sysconfdir}/logcheck/ignore.d \
		   ${D}${oldmandir} ${D}${oldsbindir}
	oe_runmake prefix=${D}${prefix} exec_prefix=${D}${exec_prefix} \
		   etcdir=${D}${sysconfdir} sbindir=${D}${sbindir} \
		   mandir=${D}${oldmandir} install
	sh ${WORKDIR}/fix-net.agent ${D}
	install -m 0755 ${WORKDIR}/update-usb.usermap ${D}${oldsbindir}/
	install -m 0644 ${WORKDIR}/logcheck-ignore ${D}${sysconfdir}/logcheck/ignore.d/hotplug
	install -m 0755 ${WORKDIR}/sleeve.agent ${D}${sysconfdir}/hotplug/
	install -m 0755 ${WORKDIR}/sleeve.rc ${D}${sysconfdir}/hotplug/
	install -m 0755 ${WORKDIR}/mmc.agent ${D}${sysconfdir}/hotplug/
	install -m 0755 ${WORKDIR}/usbd.agent ${D}${sysconfdir}/hotplug/
}

SRC_URI[md5sum] = "167bd479a1ca30243c51ca088e0942b3"
SRC_URI[sha256sum] = "397e06eefc4639342e9f650cc47336ebc8c86a37fdcd9b857e55f99d37d8da9f"
