<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * TblParticipation
 *
 * @ORM\Table(name="tbl_participation", indexes={@ORM\Index(name="fk_post_participation", columns={"idPost"}), @ORM\Index(name="fk_user_participation", columns={"idUser"}), @ORM\Index(name="fk_paytype_participation", columns={"idPayType"})})
 * @ORM\Entity
 */
class TblParticipation
{
    /**
     * @var \DateTime
     *
     * @ORM\Column(name="bookedDTM", type="datetime", nullable=false, options={"default"="current_timestamp()"})
     */
    private $bookeddtm = 'current_timestamp()';

    /**
     * @var int|null
     *
     * @ORM\Column(name="rank", type="integer", nullable=true, options={"default"="NULL"})
     */
    private $rank = NULL;

    /**
     * @var int|null
     *
     * @ORM\Column(name="result", type="integer", nullable=true, options={"default"="NULL"})
     */
    private $result = NULL;

    /**
     * @var int
     *
     * @ORM\Column(name="idParticipation", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idparticipation;

    /**
     * @var \TblUser
     *
     * @ORM\ManyToOne(targetEntity="TblUser")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idUser", referencedColumnName="idUser")
     * })
     */
    private $iduser;

    /**
     * @var \TblPost
     *
     * @ORM\ManyToOne(targetEntity="TblPost")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idPost", referencedColumnName="idPost")
     * })
     */
    private $idpost;

    /**
     * @var \TblPaytype
     *
     * @ORM\ManyToOne(targetEntity="TblPaytype")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idPayType", referencedColumnName="idPayType")
     * })
     */
    private $idpaytype;

    public function getBookeddtm(): ?\DateTimeInterface
    {
        return $this->bookeddtm;
    }

    public function setBookeddtm(\DateTimeInterface $bookeddtm): self
    {
        $this->bookeddtm = $bookeddtm;

        return $this;
    }

    public function getRank(): ?int
    {
        return $this->rank;
    }

    public function setRank(?int $rank): self
    {
        $this->rank = $rank;

        return $this;
    }

    public function getResult(): ?int
    {
        return $this->result;
    }

    public function setResult(?int $result): self
    {
        $this->result = $result;

        return $this;
    }

    public function getIdparticipation(): ?int
    {
        return $this->idparticipation;
    }

    public function getIduser(): ?TblUser
    {
        return $this->iduser;
    }

    public function setIduser(?TblUser $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }

    public function getIdpost(): ?TblPost
    {
        return $this->idpost;
    }

    public function setIdpost(?TblPost $idpost): self
    {
        $this->idpost = $idpost;

        return $this;
    }

    public function getIdpaytype(): ?TblPaytype
    {
        return $this->idpaytype;
    }

    public function setIdpaytype(?TblPaytype $idpaytype): self
    {
        $this->idpaytype = $idpaytype;

        return $this;
    }


}
