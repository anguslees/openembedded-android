SECTION = "kernel"
DESCRIPTION = "handhelds.org Linux kernel 2.6 for PocketPCs and other consumer handheld devices."
LICENSE = "GPLv2"
PV = "${K_MAJOR}.${K_MINOR}.${K_MICRO}-hh${HHV}+cvs${SRCDATE}"
PR = "r1"

DEFAULT_PREFERENCE = "-1"

# Override where to look for defconfigs and patches,
# we have per-kernel-release sets.
FILESPATHPKG =. "linux-handhelds-2.6-2.6.21/${MACHINE}:linux-handhelds-2.6-2.6.21:"

K_MAJOR = "2"
K_MINOR = "6"
K_MICRO = "21"
HHV     = "20"

SRC_URI = "${HANDHELDS_CVS};module=linux/kernel26 \
           file://linux-2.6.git-9d20fdd58e74d4d26dc5216efaaa0f800c23dd3a.patch \
           http://www.rpsys.net/openzaurus/patches/archive/export_atags-r0a.patch;name=patch \
           file://gcc4x-limits.patch \
           file://0001-time-prevent-the-loop-in-timespec_add_ns-from-bei.patch \
           file://defconfig"

require linux-handhelds-2.6.inc

SRC_URI[patch.md5sum] = "8ab51e8ff728f4155db64b9bb6ea6d71"
SRC_URI[patch.sha256sum] = "75d4c6ddbfc5e4fff7690a3308e2574f89a0e2709fb91caccb29067a9dad251a"
