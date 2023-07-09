import SpriteIcon from '../../assets/sprite-icon.svg'

type IconNameType =
  | 'chatting-plus'
  | 'chatting-dots'
  | 'bag-minus'
  | 'bag-plus'
  | 'box-arrow-left'
  | 'box-arrow-right'
  | 'calendar'
  | 'camera'
  | 'check-box'
  | 'uncheck-box'
  | 'document'
  | 'x'
  | 'skyblue-x-circle'
  | 'pencil-box'
  | 'trash-bin'
  | 'alarm-gray'
  | 'alarm-blue'
  | 'calculator-gray'
  | 'calculator-blue'
  | 'file-text-fill-gray'
  | 'file-text-fill-blue'
  | 'check-square-gray'
  | 'check-square-blue'
  | 'add-person'
  | 'profile'
  | 'eye'
  | 'eye-close'
  | 'minus-square'
  | 'plus-square'

type IconProps = {
  name: IconNameType
<<<<<<< HEAD
  size?: 16 | 18 | 20 | 24 | 28 | 42 | 50 | 64 | 70
=======
  size?: 10 | 12 | 16 | 18 | 24 | 28 | 50 | 70
>>>>>>> 0a6eb8d (Design: - 버튼 svg 아이콘으로 변경 (#41))
}

function Icon({ name, size = 24 }: IconProps) {
  return (
    <svg width={size} height={size}>
      <use href={`${SpriteIcon}#${name}`} />
    </svg>
  )
}

export default Icon
