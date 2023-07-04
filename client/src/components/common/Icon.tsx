import SpriteIcon from '../../assets/sprite-icon.svg'

type IconNameType =
  | 'chatting-plus'
  | 'chatting-dots'
  | 'blue-clock'
  | 'bag-minus'
  | 'bag-plus'
  | 'box-arrow-left'
  | 'box-arrow-right'
  | 'calculator'
  | 'calendar'
  | 'camera'
  | 'check-box'
  | 'document'
  | 'x'
  | 'skyblue-x-circle'
  | 'person-plus'
  | 'pencil-box'
  | 'person'
  | 'trash-bin'

type IconProps = {
  name: IconNameType
  size?: 16 | 24 | 28 | 50
}

function Icon({ name, size = 24 }: IconProps) {
  return (
    <svg width={size} height={size}>
      <use href={`${SpriteIcon}#${name}`} />
    </svg>
  )
}

export default Icon
