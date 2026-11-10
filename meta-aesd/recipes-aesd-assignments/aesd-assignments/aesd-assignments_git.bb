# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments repo.  Use https protocol and a public
# repo, or see assignment instructions for use with ssh keys

# Using https seemed to require a new checksum every time it fetched.
# Google suggested thie git: URL instead, which should not require a checksum.
#SRC_URI = "https://github.com/curtnichols/aeld-assignment-3-and-later;protocol=https;nobranch=1"
#SRC_URI[sha256sum] = "59de149c3b071d891a1465fc1cd7f05f18e8ebba55e921e30cdf10951110a3dc"

SRC_URI = "git://github.com/curtnichols/aeld-assignment-3-and-later.git;protocol=https;branch=main"
# The checksum is not necessary with the "git:" URI

PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
SRCREV = "ea0b674b21916e741fc053c024449093d2c6e7ca"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"


# TODO: Add the aesdsocket application and any other files you will install in do_install below
# See https://github.com/openembedded/openembedded-core/blob/wrynose/meta/conf/bitbake.conf for path prefixes like ${bindir}
# or ${sysconfdir}"
# [This appears to be /usr/bin/aesdsocket.]

FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket-start-stop"

# TODO: customize these as necessary for any libraries you need for your application
#TARGET_LDFLAGS += "-pthread -lrt"

S = "${UNPACKDIR}/aesd-assignments-1.0+git/server"

do_configure () {
	:
}

do_compile () {
    # TODO: switch to the server directory where your source code to be built is located

	bbplain "UNPACKDIR = ${UNPACKDIR}"
	bbplain "The source directory is: ${S}"
        bbplain "The work directory is: ${D}"
	bbplain "The package name is: ${PN}"

	#cd /home/curt/ecea-5305/git/assignment-1-curtnichols/server/
	oe_runmake
}

#TODO: add initscript necessary changes here

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See examples at https://github.com/cu-ecen-aeld/yocto-hello-world for your relevant build as well
	# Remember to copy files relative to their location after building, which may be under the server subdirectory

	install -d ${D}${bindir}
	install -m 0755 ${B}/aesdsocket ${D}${bindir}/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d
}
