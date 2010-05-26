require linux.inc

PR = "r10"

S = "${WORKDIR}/linux-${PV}"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_ion = "1"
DEFAULT_PREFERENCE_simone = "1"
DEFAULT_PREFERENCE_eee701 = "1"
DEFAULT_PREFERENCE_at91sam9g45ek = "1"

DEFAULT_PREFERENCE_akita = "-1"
DEFAULT_PREFERENCE_c7x0 = "-1"
DEFAULT_PREFERENCE_collie = "-1"
DEFAULT_PREFERENCE_poodle = "-1"
DEFAULT_PREFERENCE_spitz = "-1"
DEFAULT_PREFERENCE_tosa = "-1"
DEFAULT_PREFERENCE_ben-nanonote = "-1"
DEFAULT_PREFERENCE_jornada6xx = "-1"
DEFAULT_PREFERENCE_jornada7xx = "-1"
DEFAULT_PREFERENCE_ts72xx = "-1"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2;name=kernel \
           ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/patch-${PV}.9.bz2;apply=yes;name=stablepatch \
           file://defconfig"

SRC_URI[kernel.md5sum] = "260551284ac224c3a43c4adac7df4879"
SRC_URI[kernel.sha256sum] = "5099786d80b8407d98a619df00209c2353517f22d804fdd9533b362adcb4504e"
SRC_URI[stablepatch.md5sum] = "7f615dd3b4a3b19fb86e479996a2deb5"
SRC_URI[stablepatch.sha256sum] = "8aeb15c31fb09c769f004c8dc51e29aa26be8e84d70db418af70ecefc463459a"

SRC_URI_append_at91sam9g45ek = " \
	file://at91/linux-2.6.32-001-configurable-nand-partitions.patch \
	file://at91/linux-2.6.32-002-sam9g20-proper-reset.patch \
	"


# part of 2.6.24.7 patchset from Sim.One project
# other patches needs work
SRC_URI_append_simone = " \
			file://ep93xx/edb9301-fix-machine-id.patch \
			file://ep93xx/simone-board-def.patch \
			file://ep93xx/ep93xx-regs.patch \
			file://ep93xx/ep93xx-i2c.patch \
			file://ep93xx/ep93xx-touchscreen.patch \
			file://ep93xx/ep93xx-spi.patch \
			file://ep93xx/ep93xx-cpuinfo.patch "

FILES_kernel-image_simone = ""

SRC_URI_append_ts72xx = " \
                        file://0001-ts72xx_base.patch \
                        file://0002-ts72xx_force_machine-id.patch \
                        file://0003-ep93xx_cpuinfo.patch \
                        file://0004-ts72xx_sbcinfo.patch \
                        file://0005-ep93xx_eth.patch \
                        file://0006-ts72xx_ts_ser1.patch \
                        file://0007-ts72xx_rs485.patch \
                        file://0008-ts72xx_ts_eth100.patch \
                        file://0009-ts7200_cf_ide.patch \
                        file://0010-ts72xx_pata.patch \
                        file://0011-ep93xx_pm.patch \
                        file://0012-ts72xx_gpio_i2c.patch \
                        file://0013-ts72xx_dio_keypad.patch \
                        file://0014-ep93xx_spi.patch \
                        file://0015-ep93xx_cpufreq.patch \
                        file://0016-ts7200_nor_flash.patch \
                        "

# Zaurus family bootloader patches
RPSRC = "http://www.rpsys.net/openzaurus/patches/archive"
ZAURUSPATCHES = " ${RPSRC}/pxa-linking-bug-r1.patch;status=unmergable;name=pxa-linking-bug-r1 "
SRC_URI[pxa-linking-bug-r1.md5sum] = "1e2a99787260c3566033e7f41180e2c8"
SRC_URI[pxa-linking-bug-r1.sha256sum] = "785d2680022325ad54c1593082dce902f5fee31dae4c1922ba43956b1dcfcd8b"

# Machine specific patches
SRC_URI_append_akita = "${ZAURUSPATCHES}"
SRC_URI_append_c7x0 = "${ZAURUSPATCHES}"
SRC_URI_append_collie = "${ZAURUSPATCHES}"
SRC_URI_append_poodle = "${ZAURUSPATCHES}"
SRC_URI_append_spitz = "${ZAURUSPATCHES}"
SRC_URI_append_tosa = "${ZAURUSPATCHES}"

SRC_URI_append_eee701 = " \
	file://intelfb.patch "
