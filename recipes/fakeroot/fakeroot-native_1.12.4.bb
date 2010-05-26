require fakeroot_${PV}.bb

SRC_URI += "file://fix-prefix.patch"
S = "${WORKDIR}/fakeroot-${PV}"

inherit native

EXTRA_OECONF = "--program-prefix="

# Compatability for the rare systems not using or having SYSV
python () {
    if bb.data.getVar('HOST_NONSYSV', d, True) and bb.data.getVar('HOST_NONSYSV', d, True) != '0':
        bb.data.setVar('EXTRA_OECONF', ' --with-ipc=tcp --program-prefix= ', d)
}

do_stage_append () {
    oe_libinstall -so libfakeroot ${STAGING_LIBDIR}/libfakeroot/
}

RDEPENDS = "util-linux-native"

SRC_URI[md5sum] = "aaefede2405a40c87438e7e833d69b70"
SRC_URI[sha256sum] = "dbcab1f495b857e67feff882e018ca59958b8d189ff1f76684d28e35463ec29d"
