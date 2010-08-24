#Angstrom minimal gpe image
# This image provides a barebone dm and 'desktop' 
# Very angstrom and opkg centric

XSERVER ?= "xserver-kdrive-fbdev"
SPLASH ?= ' ${@base_contains("MACHINE_FEATURES", "screen", "psplash-angstrom", "",d)}'

export IMAGE_BASENAME = "minimalist-gpe-image"

PR = "r2"

DEPENDS = "task-boot"
IMAGE_INSTALL = "\
    ${XSERVER} \
    task-boot \
    gpe-dm gpe-session-scripts gpe-login \
    matchbox-wm \
    gpe-terminal \
    angstrom-feed-configs \
    ${SPLASH} \
    "

#zap root password for release images
ROOTFS_POSTPROCESS_COMMAND += '${@base_conditional("DISTRO_TYPE", "release", "zap_root_password; ", "",d)}'

inherit image
